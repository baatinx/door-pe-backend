(ns doorpe.backend.server.file-upload
  (:require [clojure.java.io :as io]
            [monger.util :refer [object-id]]
            [clojure.string :as string])
  (:import [java.io File]))

(defn file-upload
  [file prefix]
  (let [file-name (:filename file)
        extension (last (string/split file-name #"\."))
        timestamp (-> (object-id)
                      str)
        prefix-present (and (not (empty? prefix))
                            (str prefix "-"))
        new-file-name (str prefix-present timestamp "." extension)
        ;; content-type (:content-type file)
        ;; size (:size file)
        temp-file (-> file
                      :tempfile
                      io/file)
        path "./resources/img/"]
    (do
      (io/copy temp-file (File. (str path new-file-name)))
      {:file-status true :file-name new-file-name})))
