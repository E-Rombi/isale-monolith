ALTER TABLE IF EXISTS public.product
    ADD company_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_product_company FOREIGN KEY (company_id) REFERENCES company (id);
