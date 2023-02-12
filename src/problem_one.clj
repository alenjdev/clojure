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
(defn eval-invalid-condition
  [condition-one condition-two]
  (and condition-one condition-two))

(defn is-valid-invoice
  [item]
  (let [is-current-tax-rate-valid (is-valid-tax-rate (get-tax-rate item))]
    (let [is-current-ret-fuente-valid (is-valid-ret-fuente (get-ret-fuente item))]
      (if (eval-invalid-condition is-current-ret-fuente-valid is-current-tax-rate-valid)
        false
        (or is-current-ret-fuente-valid is-current-tax-rate-valid)))))

(defn get-valid-invoices
  [{invoices :invoice/items}]
  (filter #(is-valid-invoice %) invoices))
(get-valid-invoices invoice)