DROP SEQUENCE IF EXISTS public.number_seq;
CREATE SEQUENCE public.number_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP TABLE IF EXISTS public.account;
CREATE TABLE IF NOT EXISTS public.account
(
    id uuid NOT NULL,
    agency integer NOT NULL,
    cpf character varying(255) COLLATE pg_catalog."default" NOT NULL,
    create_at timestamp without time zone,
    data_nascimento date NOT NULL,
    is_block boolean,
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "number" integer NOT NULL DEFAULT nextval('number_seq'::regclass),
    saldo numeric(10,2) DEFAULT 0.00,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT uk_e7a6n14jbd31half1ldvbamqb UNIQUE (cpf)
);