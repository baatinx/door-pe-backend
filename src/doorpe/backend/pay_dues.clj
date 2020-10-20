(ns doorpe.backend.pay-dues
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [str->int extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [tick.core :as time]
            [monger.util :refer [object-id]]
            [doorpe.backend.server.send-email :refer [send-email]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.ingestion :as insert]))

(defn payment-gateway
  [amount]
  {:transaction-id (str "TXN-" (rand-int 999999))
   :amount-paid amount
   :payment-status true})

(defn pay-dues
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          {user-id :user-id user-type :user-type} (token->token-details token)
          amount  (-> req
                      :params
                      :amount
                      str->int)

          coll "payments"
          id (object-id)
          paid-on (time/today)

          {:keys [transaction-id amount-paid payment-status]} (and (= "service-provider" user-type)
                                                                   (pos? amount)
                                                                   (payment-gateway amount))

          doc (and (= true payment-status)
                   {:_id id
                    :transaction-id transaction-id
                    :service-provider-id (object-id user-id)
                    :paid-on paid-on
                    :amount amount-paid})
          res  (insert/doc coll doc)]
      (if res
        (response/response {:status true})
        (response/response {:status false})))))

