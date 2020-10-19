(ns doorpe.backend.complaint-reply
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [str->int extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [tick.core :as time]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$set]]
            [doorpe.backend.server.send-email :refer [send-email]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn complaint-reply
  [req]
  (println req)
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          user-type  (-> token
                         token->token-details
                         :user-type)
          {:keys [complaint-id admin-reply]} (:params req)
          coll "complaints"
          complaint-details (query/retreive-by-id coll complaint-id)
          user-name (:user-name complaint-details)
          email (:email complaint-details)
          doc {$set {:addressed true
                     :admin-reply admin-reply}}
          subject "Doorpe - Complaint Response from Admin"
          body (str  "<p>Hi! <strong>" user-name "</strong><br/>Admin here, reply to your complaint no - <strong>" complaint-id "</strong><br/>" admin-reply "</p>")
          res (and (= "admin" user-type)
                   (send-email email subject body)
                   (command/update-by-id coll complaint-id  doc))]
      (if res
        (response/response {:status true})
        (response/response {:insert-status false})))))