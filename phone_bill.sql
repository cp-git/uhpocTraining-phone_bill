PGDMP     &    9            	    z            phone_bill_system    14.5    14.5     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16394    phone_bill_system    DATABASE     m   CREATE DATABASE phone_bill_system WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_India.1252';
 !   DROP DATABASE phone_bill_system;
                postgres    false            �            1259    16475    call_details    TABLE     �   CREATE TABLE public.call_details (
    call_details_id integer NOT NULL,
    call_date date NOT NULL,
    call_phno bigint NOT NULL,
    call_in_out character varying NOT NULL,
    call_duration smallint NOT NULL,
    cust_accno integer NOT NULL
);
     DROP TABLE public.call_details;
       public         heap    postgres    false            �            1259    16474     call_details_call_details_id_seq    SEQUENCE     �   CREATE SEQUENCE public.call_details_call_details_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.call_details_call_details_id_seq;
       public          postgres    false    212            �           0    0     call_details_call_details_id_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.call_details_call_details_id_seq OWNED BY public.call_details.call_details_id;
          public          postgres    false    211            �            1259    16462    customer_cust_accno_seq    SEQUENCE     �   CREATE SEQUENCE public.customer_cust_accno_seq
    AS integer
    START WITH 10000
    INCREMENT BY 1
    MINVALUE 10000
    NO MAXVALUE
    CACHE 10000;
 .   DROP SEQUENCE public.customer_cust_accno_seq;
       public          postgres    false            �            1259    16463    customer    TABLE     �  CREATE TABLE public.customer (
    cust_accno integer DEFAULT nextval('public.customer_cust_accno_seq'::regclass) NOT NULL,
    cust_name character varying NOT NULL,
    cust_phno bigint NOT NULL,
    cust_address1 character varying NOT NULL,
    cust_address2 character varying NOT NULL,
    cust_city character varying NOT NULL,
    cust_state character varying NOT NULL,
    cust_pincode integer NOT NULL
);
    DROP TABLE public.customer;
       public         heap    postgres    false    209            b           2604    16478    call_details call_details_id    DEFAULT     �   ALTER TABLE ONLY public.call_details ALTER COLUMN call_details_id SET DEFAULT nextval('public.call_details_call_details_id_seq'::regclass);
 K   ALTER TABLE public.call_details ALTER COLUMN call_details_id DROP DEFAULT;
       public          postgres    false    211    212    212            �          0    16475    call_details 
   TABLE DATA           u   COPY public.call_details (call_details_id, call_date, call_phno, call_in_out, call_duration, cust_accno) FROM stdin;
    public          postgres    false    212   �       �          0    16463    customer 
   TABLE DATA           �   COPY public.customer (cust_accno, cust_name, cust_phno, cust_address1, cust_address2, cust_city, cust_state, cust_pincode) FROM stdin;
    public          postgres    false    210                      0    0     call_details_call_details_id_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.call_details_call_details_id_seq', 1, false);
          public          postgres    false    211                       0    0    customer_cust_accno_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.customer_cust_accno_seq', 20000, true);
          public          postgres    false    209            h           2606    16482    call_details call_details_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.call_details
    ADD CONSTRAINT call_details_pkey PRIMARY KEY (call_details_id);
 H   ALTER TABLE ONLY public.call_details DROP CONSTRAINT call_details_pkey;
       public            postgres    false    212            d           2606    16472    customer customer_cust_phno_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_cust_phno_key UNIQUE (cust_phno);
 I   ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_cust_phno_key;
       public            postgres    false    210            f           2606    16470    customer customer_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (cust_accno);
 @   ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_pkey;
       public            postgres    false    210            i           2606    16483    call_details fk_customer    FK CONSTRAINT     �   ALTER TABLE ONLY public.call_details
    ADD CONSTRAINT fk_customer FOREIGN KEY (cust_accno) REFERENCES public.customer(cust_accno) ON UPDATE CASCADE ON DELETE CASCADE;
 B   ALTER TABLE ONLY public.call_details DROP CONSTRAINT fk_customer;
       public          postgres    false    212    210    3174            �      x������ � �      �   5   x�34000��M�,-�44261�LLI1F��y����%�%�93�=... m�A     