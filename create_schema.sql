--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2022-06-25 18:00:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16397)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(64) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16396)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO postgres;

--
-- TOC entry 3329 (class 0 OID 0)
-- Dependencies: 209
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 211 (class 1259 OID 16403)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    login character varying(64) NOT NULL,
    name character varying(64),
    password character varying(64) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16408)
-- Name: users_to_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_to_roles (
    user_login character varying(64) NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.users_to_roles OWNER TO postgres;

--
-- TOC entry 3172 (class 2604 OID 16414)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 3321 (class 0 OID 16397)
-- Dependencies: 210
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	Admin
2	Operator
3	Analytic
4	Programmer
\.


--
-- TOC entry 3322 (class 0 OID 16403)
-- Dependencies: 211
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (login, name, password) FROM stdin;
User1	Vasya	123
User2	Petya	321
User3	Egor	dawdaf324
\.


--
-- TOC entry 3323 (class 0 OID 16408)
-- Dependencies: 212
-- Data for Name: users_to_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_to_roles (user_login, role_id) FROM stdin;
User1	1
User1	4
User1	3
User2	4
User3	2
User3	4
\.


--
-- TOC entry 3330 (class 0 OID 0)
-- Dependencies: 209
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 4, true);


--
-- TOC entry 3174 (class 2606 OID 16416)
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- TOC entry 3176 (class 2606 OID 16407)
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (login);


--
-- TOC entry 3178 (class 2606 OID 16422)
-- Name: users_to_roles users_to_roles_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_to_roles
    ADD CONSTRAINT users_to_roles_pk PRIMARY KEY (user_login, role_id);


--
-- TOC entry 3179 (class 2606 OID 16427)
-- Name: users_to_roles users_to_roles_roles_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_to_roles
    ADD CONSTRAINT users_to_roles_roles_id_fk FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- TOC entry 3180 (class 2606 OID 16432)
-- Name: users_to_roles users_to_roles_users_login_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_to_roles
    ADD CONSTRAINT users_to_roles_users_login_fk FOREIGN KEY (user_login) REFERENCES public.users(login) ON DELETE CASCADE;


-- Completed on 2022-06-25 18:00:35

--
-- PostgreSQL database dump complete
--

