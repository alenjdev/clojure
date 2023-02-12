(ns clojure-sample.core
  (:gen-class))
(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

(defn get-nested-value
  [item path-one path-two]
  (get (get (get item path-one) 0) path-two))

(defn get-tax-rate
  [item]
  (get-nested-value item :taxable/taxes :tax/rate))

(defn get-ret-fuente
  [item]
  (get-nested-value item :retentionable/retentions :retention/rate))

(defn is-valid-tax-rate
  [tax-value]
  (= tax-value 19))
(defn is-valid-ret-fuente
  [ret-value]
  (= ret-value 1))

(defn is-valid-invoice
  [item]
  (if (= true (= true (is-valid-tax-rate (get-tax-rate item)))
         (= true  (is-valid-ret-fuente (get-ret-fuente item))))
    false
    (or (is-valid-tax-rate (get-tax-rate item))
        (is-valid-ret-fuente (get-ret-fuente item)))))

(defn get-valid-invoices
  [{invoices :invoice/items}]
  (filter #(is-valid-invoice %) invoices))
(get-valid-invoices invoice)