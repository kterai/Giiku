--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)

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

--
-- Name: update_updated_at_column(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_updated_at_column() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;



SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: application_approval_routes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_approval_routes (
    id bigint NOT NULL,
    application_id bigint NOT NULL,
    step_order integer NOT NULL,
    approver_id bigint,
    status character varying(20) NOT NULL,
    action character varying(20),
    comment character varying(1000),
    processed_at timestamp with time zone,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: application_approval_routes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_approval_routes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: application_approval_routes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_approval_routes_id_seq OWNED BY public.application_approval_routes.id;

--
-- Name: application_approvals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_approvals (
    id bigint NOT NULL,
    application_id bigint NOT NULL,
    step_order integer NOT NULL,
    approver_id bigint,
    status character varying(20) NOT NULL,
    action character varying(20),
    comment text,
    due_date timestamp with time zone,
    approved_at timestamp with time zone,
    rejected_at timestamp with time zone,
    delegated_to bigint,
    delegated_at timestamp with time zone,
    is_delegated boolean DEFAULT false NOT NULL,
    notification_sent boolean DEFAULT false NOT NULL,
    notification_sent_at timestamp with time zone,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: TABLE application_approvals; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_approvals IS '承認履歴テーブル';


--
-- Name: COLUMN application_approvals.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.id IS '承認履歴ID';


--
-- Name: COLUMN application_approvals.application_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.application_id IS '申請ID';


--
-- Name: COLUMN application_approvals.step_order; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.step_order IS '承認ステップ順序';


--
-- Name: COLUMN application_approvals.approver_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.approver_id IS '承認者ID';


--
-- Name: COLUMN application_approvals.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.status IS '承認状態（PENDING/APPROVED/REJECTED）';


--
-- Name: COLUMN application_approvals.action; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.action IS '承認アクション';


--
-- Name: COLUMN application_approvals.comment; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.comment IS '承認コメント';


--
-- Name: COLUMN application_approvals.due_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.due_date IS '承認期限';


--
-- Name: COLUMN application_approvals.approved_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.approved_at IS '承認日時';


--
-- Name: COLUMN application_approvals.rejected_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.rejected_at IS '却下日時';


--
-- Name: COLUMN application_approvals.delegated_to; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.delegated_to IS '委任先承認者ID';


--
-- Name: COLUMN application_approvals.delegated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.delegated_at IS '委任日時';


--
-- Name: COLUMN application_approvals.is_delegated; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.is_delegated IS '代理承認フラグ';


--
-- Name: COLUMN application_approvals.notification_sent; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.notification_sent IS '通知送信フラグ';


--
-- Name: COLUMN application_approvals.notification_sent_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.notification_sent_at IS '通知送信日時';


--
-- Name: COLUMN application_approvals.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.created_by IS '作成者ID';


--
-- Name: COLUMN application_approvals.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.created_at IS '作成日時';


--
-- Name: COLUMN application_approvals.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.updated_by IS '更新者ID';


--
-- Name: COLUMN application_approvals.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_approvals.updated_at IS '更新日時';


--
-- Name: application_approvals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_approvals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: application_approvals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_approvals_id_seq OWNED BY public.application_approvals.id;


--
-- Name: application_attachments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_attachments (
    id bigint NOT NULL,
    application_id bigint NOT NULL,
    original_filename character varying(255),
    stored_filename character varying(255),
    file_path character varying(500) NOT NULL,
    file_size bigint NOT NULL,
    content_type character varying(100),
    file_extension character varying(10),
    file_hash character varying(128),
    hash_algorithm character varying(20),
    description character varying(500),
    display_order integer,
    downloadable boolean DEFAULT true NOT NULL,
    scan_status character varying(20),
    scanned_at timestamp with time zone,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: application_attachments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_attachments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: application_attachments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_attachments_id_seq OWNED BY public.application_attachments.id;


--
-- Name: application_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application_types (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    form_config jsonb,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    code character varying(50),
    display_order integer,
    active boolean DEFAULT true NOT NULL,
    auto_approve boolean DEFAULT false NOT NULL
);



--
-- Name: TABLE application_types; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.application_types IS '申請種別マスタ';


--
-- Name: COLUMN application_types.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.id IS '申請種別ID';


--
-- Name: COLUMN application_types.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.name IS '申請種別名';


--
-- Name: COLUMN application_types.description; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.description IS '申請種別説明';


--
-- Name: COLUMN application_types.form_config; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.form_config IS 'フォーム設定（JSON形式）';


--
-- Name: COLUMN application_types.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.created_by IS '作成者ID';


--
-- Name: COLUMN application_types.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.created_at IS '作成日時';


--
-- Name: COLUMN application_types.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.updated_by IS '更新者ID';


--
-- Name: COLUMN application_types.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.application_types.updated_at IS '更新日時';


--
-- Name: application_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: application_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_types_id_seq OWNED BY public.application_types.id;


--
-- Name: applications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.applications (
    id bigint NOT NULL,
    application_type_id bigint NOT NULL,
    applicant_id bigint NOT NULL,
    status character varying(20) DEFAULT 'DRAFT'::character varying NOT NULL,
    form_data jsonb,
    application_number character varying(50),
    title character varying(200),
    content text,
    current_step integer,
    reason character varying(1000),
    priority character varying(10),
    requested_date timestamp with time zone,
    application_date timestamp with time zone,
    approver_id bigint,
    due_date timestamp with time zone,
    department_id bigint,
    is_urgent boolean DEFAULT false NOT NULL,
    amount numeric,
    currency_code character varying(3),
    start_date timestamp with time zone,
    end_date timestamp with time zone,
    remarks character varying(2000),
    approved_at timestamp with time zone,
    rejected_at timestamp with time zone,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: TABLE applications; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.applications IS '申請テーブル';


--
-- Name: COLUMN applications.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.applications.id IS '申請ID';


--
-- Name: COLUMN applications.application_type_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.applications.application_type_id IS '申請種別ID';


--
-- Name: COLUMN applications.applicant_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.applications.applicant_id IS '申請者ID';


--
-- Name: COLUMN applications.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.applications.status IS '申請状態（DRAFT/SUBMITTED/APPROVED/REJECTED/CANCELLED）';


--
-- Name: COLUMN applications.form_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.applications.form_data IS '申請フォームデータ（JSON形式）';


--
-- Name: applications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.applications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: applications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.applications_id_seq OWNED BY public.applications.id;


--
-- Name: approval_flows; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.approval_flows (
    id bigint NOT NULL,
    application_type_id bigint NOT NULL,
    department_id bigint,
    flow_name character varying(100) NOT NULL,
    description text,
    is_active boolean DEFAULT true NOT NULL,
    display_order integer DEFAULT 0,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: TABLE approval_flows; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.approval_flows IS '承認フローマスタ';


--
-- Name: COLUMN approval_flows.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.id IS '承認フローID';


--
-- Name: COLUMN approval_flows.application_type_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.application_type_id IS '申請種別ID';


--
-- Name: COLUMN approval_flows.department_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.department_id IS '部署ID';


--
-- Name: COLUMN approval_flows.flow_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.flow_name IS 'フロー名';


--
-- Name: COLUMN approval_flows.description; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.description IS '説明';


--
-- Name: COLUMN approval_flows.is_active; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.is_active IS 'アクティブフラグ';


--
-- Name: COLUMN approval_flows.display_order; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.display_order IS '表示順序';


--
-- Name: COLUMN approval_flows.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.created_by IS '作成者ID';


--
-- Name: COLUMN approval_flows.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.created_at IS '作成日時';


--
-- Name: COLUMN approval_flows.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.updated_by IS '更新者ID';


--
-- Name: COLUMN approval_flows.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_flows.updated_at IS '更新日時';


--
-- Name: approval_flows_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.approval_flows_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: approval_flows_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.approval_flows_id_seq OWNED BY public.approval_flows.id;


--
-- Name: approval_routes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.approval_routes (
    id bigint NOT NULL,
    application_type_id bigint NOT NULL,
    step_order integer NOT NULL,
    approver_role character varying(20),
    approver_department_id bigint,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    active boolean DEFAULT true NOT NULL,
    required boolean DEFAULT true NOT NULL,
    specific_approver_id bigint,
    parallel boolean DEFAULT false NOT NULL,
    department_head boolean DEFAULT false NOT NULL,
    confirm_only boolean DEFAULT false NOT NULL
);



--
-- Name: TABLE approval_routes; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.approval_routes IS '承認ルートマスタ';


--
-- Name: COLUMN approval_routes.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.id IS '承認ルートID';


--
-- Name: COLUMN approval_routes.application_type_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.application_type_id IS '申請種別ID';


--
-- Name: COLUMN approval_routes.step_order; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.step_order IS '承認ステップ順序';


--
-- Name: COLUMN approval_routes.approver_role; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.approver_role IS '承認者ロール';


--
-- Name: COLUMN approval_routes.approver_department_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.approver_department_id IS '承認者部署ID';


--
-- Name: COLUMN approval_routes.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.created_by IS '作成者ID';


--
-- Name: COLUMN approval_routes.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.created_at IS '作成日時';


--
-- Name: COLUMN approval_routes.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.updated_by IS '更新者ID';


--
-- Name: COLUMN approval_routes.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_routes.updated_at IS '更新日時';


--
-- Name: approval_routes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.approval_routes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: approval_routes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.approval_routes_id_seq OWNED BY public.approval_routes.id;


--
-- Name: approval_steps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.approval_steps (
    id bigint NOT NULL,
    approval_flow_id bigint NOT NULL,
    step_order integer NOT NULL,
    step_name character varying(100) NOT NULL,
    approver_type character varying(20) NOT NULL,
    approver_condition jsonb,
    is_confirmation_only boolean DEFAULT false,
    is_parallel boolean DEFAULT false,
    timeout_hours integer,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: TABLE approval_steps; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.approval_steps IS '承認ステップマスタ';


--
-- Name: COLUMN approval_steps.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.id IS 'ステップID';


--
-- Name: COLUMN approval_steps.approval_flow_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.approval_flow_id IS '承認フローID';


--
-- Name: COLUMN approval_steps.step_order; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.step_order IS 'ステップ順序';


--
-- Name: COLUMN approval_steps.step_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.step_name IS 'ステップ名';


--
-- Name: COLUMN approval_steps.approver_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.approver_type IS '承認者種別';


--
-- Name: COLUMN approval_steps.approver_condition; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.approver_condition IS '承認者条件';


--
-- Name: COLUMN approval_steps.is_confirmation_only; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.is_confirmation_only IS '確認のみフラグ';


--
-- Name: COLUMN approval_steps.is_parallel; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.is_parallel IS '並列フラグ';


--
-- Name: COLUMN approval_steps.timeout_hours; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.timeout_hours IS 'タイムアウト時間(時間)';


--
-- Name: COLUMN approval_steps.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.created_by IS '作成者ID';


--
-- Name: COLUMN approval_steps.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.created_at IS '作成日時';


--
-- Name: COLUMN approval_steps.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.updated_by IS '更新者ID';


--
-- Name: COLUMN approval_steps.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.approval_steps.updated_at IS '更新日時';


--
-- Name: approval_steps_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.approval_steps_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: approval_steps_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.approval_steps_id_seq OWNED BY public.approval_steps.id;


--
-- Name: audit_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.audit_logs (
    id bigint NOT NULL,
    table_name character varying(100) NOT NULL,
    operation character varying(10) NOT NULL,
    record_id character varying(100),
    old_values text,
    new_values text,
    user_id bigint,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_columns character varying(1000),
    username character varying(100),
    session_id character varying(100),
    ip_address character varying(45),
    user_agent character varying(500),
    reason character varying(1000),
    transaction_id character varying(100),
    executed_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    application_name character varying(50),
    function_name character varying(100),
    severity_level character varying(10),
    event_type character varying(50)
);



--
-- Name: TABLE audit_logs; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.audit_logs IS '監査ログテーブル';


--
-- Name: COLUMN audit_logs.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.id IS '監査ログID';


--
-- Name: COLUMN audit_logs.table_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.table_name IS 'テーブル名';


--
-- Name: COLUMN audit_logs.operation; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.operation IS '操作種別（INSERT/UPDATE/DELETE）';


--
-- Name: COLUMN audit_logs.record_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.record_id IS 'レコードID';


--
-- Name: COLUMN audit_logs.old_values; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.old_values IS '変更前の値（JSON形式）';


--
-- Name: COLUMN audit_logs.new_values; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.new_values IS '変更後の値（JSON形式）';


--
-- Name: COLUMN audit_logs.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.user_id IS '操作ユーザーID';


--
-- Name: COLUMN audit_logs.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.audit_logs.created_at IS '作成日時';


--
-- Name: audit_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.audit_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: audit_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.audit_logs_id_seq OWNED BY public.audit_logs.id;


--
-- Name: department_responsible_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.department_responsible_users (
    id bigint NOT NULL,
    department_id bigint NOT NULL,
    user_id bigint NOT NULL
);



--
-- Name: department_responsible_users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.department_responsible_users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: department_responsible_users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.department_responsible_users_id_seq OWNED BY public.department_responsible_users.id;

--
-- Name: TABLE department_responsible_users; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.department_responsible_users IS '部署責任者紐付け';


--
-- Name: COLUMN department_responsible_users.department_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.department_responsible_users.department_id IS '部署ID';


--
-- Name: COLUMN department_responsible_users.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.department_responsible_users.user_id IS 'ユーザーID';


--
-- Name: departments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.departments (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    parent_id bigint,
    manager_id bigint,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    display_order integer,
    active boolean DEFAULT true NOT NULL,
    code character varying(20)
);



--
-- Name: TABLE departments; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.departments IS '部署マスタ';


--
-- Name: COLUMN departments.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.id IS '部署ID';


--
-- Name: COLUMN departments.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.name IS '部署名';


--
-- Name: COLUMN departments.parent_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.parent_id IS '親部署ID';


--
-- Name: COLUMN departments.manager_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.manager_id IS '部署長ユーザーID';


--
-- Name: COLUMN departments.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.created_by IS '作成者ID';


--
-- Name: COLUMN departments.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.created_at IS '作成日時';


--
-- Name: COLUMN departments.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.updated_by IS '更新者ID';


--
-- Name: COLUMN departments.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.updated_at IS '更新日時';


--
-- Name: COLUMN departments.code; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.departments.code IS '部署コード';


--
-- Name: departments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.departments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: departments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.departments_id_seq OWNED BY public.departments.id;


--
-- Name: positions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.positions (
    id bigint NOT NULL,
    position_name character varying(100) NOT NULL,
    position_code character varying(50),
    hierarchy_level integer,
    is_manager boolean DEFAULT false NOT NULL,
    display_order integer,
    is_active boolean DEFAULT true NOT NULL,
    created_by bigint,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: TABLE positions; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.positions IS '役職マスタ';


--
-- Name: COLUMN positions.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.id IS '役職ID';


--
-- Name: COLUMN positions.position_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.position_name IS '役職名';


--
-- Name: COLUMN positions.position_code; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.position_code IS '役職コード';


--
-- Name: COLUMN positions.hierarchy_level; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.hierarchy_level IS '階層レベル';


--
-- Name: COLUMN positions.is_manager; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.is_manager IS '管理職フラグ';


--
-- Name: COLUMN positions.display_order; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.display_order IS '表示順序';


--
-- Name: COLUMN positions.is_active; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.is_active IS '有効フラグ';


--
-- Name: COLUMN positions.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.created_by IS '作成者ID';


--
-- Name: COLUMN positions.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.created_at IS '作成日時';


--
-- Name: COLUMN positions.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.updated_by IS '更新者ID';


--
-- Name: COLUMN positions.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.positions.updated_at IS '更新日時';


--
-- Name: positions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.positions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: positions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.positions_id_seq OWNED BY public.positions.id;


--
-- Name: system_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.system_logs (
    id bigint NOT NULL,
    log_level character varying(10) NOT NULL,
    category character varying(255) NOT NULL,
    message text NOT NULL,
    stack_trace text,
    user_id bigint,
    request_id character varying(100),
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    details text,
    username character varying(100),
    session_id character varying(100),
    ip_address character varying(45),
    user_agent character varying(500),
    http_method character varying(10),
    request_url character varying(1000),
    http_status integer,
    processing_time bigint,
    application_name character varying(50),
    module_name character varying(100),
    function_name character varying(100),
    server_name character varying(100),
    thread_name character varying(100),
    logged_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);



--
-- Name: TABLE system_logs; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.system_logs IS 'システムログテーブル';


--
-- Name: COLUMN system_logs.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.id IS 'システムログID';


--
-- Name: COLUMN system_logs.log_level; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.log_level IS 'ログレベル（ERROR/WARN/INFO/DEBUG）';


--
-- Name: COLUMN system_logs.category; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.category IS 'ロガー名';


--
-- Name: COLUMN system_logs.message; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.message IS 'ログメッセージ';


--
-- Name: COLUMN system_logs.stack_trace; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.stack_trace IS '例外情報';


--
-- Name: COLUMN system_logs.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.user_id IS 'ユーザーID';


--
-- Name: COLUMN system_logs.request_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.request_id IS 'リクエストID';


--
-- Name: COLUMN system_logs.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_logs.created_at IS '作成日時';


--
-- Name: system_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.system_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: system_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.system_logs_id_seq OWNED BY public.system_logs.id;


--
-- Name: travel_request_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.travel_request_details (
    id bigint NOT NULL,
    application_id bigint NOT NULL,
    applicant_id bigint,
    approver_id bigint,
    applicant_name character varying(100) NOT NULL,
    destination character varying(200) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    transport character varying(20) NOT NULL,
    need_advance boolean DEFAULT false,
    purpose character varying(1000),
    estimated_cost numeric,
    status character varying(20),
    current_step integer,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);



--
-- Name: travel_request_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.travel_request_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: travel_request_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.travel_request_details_id_seq OWNED BY public.travel_request_details.id;

--
-- Name: expense_request_details; Type: TABLE; Schema: public; Owner: postgres
--
CREATE TABLE public.expense_request_details (
    id bigint NOT NULL,
    application_id bigint NOT NULL,
    applicant_id bigint,
    approver_id bigint,
    applicant_name character varying(100) NOT NULL,
    expense_date date NOT NULL,
    amount numeric,
    description character varying(1000),
    status character varying(20),
    current_step integer,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- Name: expense_request_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.expense_request_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: expense_request_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.expense_request_details_id_seq OWNED BY public.expense_request_details.id;

--
-- Name: travel_requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.travel_requests (
    id bigint NOT NULL,
    applicant_id bigint,
    approver_id bigint,
    applicant_name character varying(100) NOT NULL,
    destination character varying(200) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    transport character varying(20) NOT NULL,
    need_advance boolean DEFAULT false,
    purpose character varying(1000),
    estimated_cost numeric,
    status character varying(20),
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    current_step integer
);



--
-- Name: TABLE travel_requests; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.travel_requests IS '出張申請テーブル';


--
-- Name: COLUMN travel_requests.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.id IS '申請ID';


--
-- Name: COLUMN travel_requests.applicant_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.applicant_id IS '申請者ID';


--
-- Name: COLUMN travel_requests.approver_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.approver_id IS '承認者ID';


--
-- Name: COLUMN travel_requests.applicant_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.applicant_name IS '申請者名';


--
-- Name: COLUMN travel_requests.destination; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.destination IS '出張先';


--
-- Name: COLUMN travel_requests.start_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.start_date IS '出発日';


--
-- Name: COLUMN travel_requests.end_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.end_date IS '帰着日';


--
-- Name: COLUMN travel_requests.transport; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.transport IS '交通手段';


--
-- Name: COLUMN travel_requests.need_advance; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.need_advance IS '前払い希望フラグ';


--
-- Name: COLUMN travel_requests.purpose; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.purpose IS '出張目的';


--
-- Name: COLUMN travel_requests.estimated_cost; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.estimated_cost IS '概算費用';


--
-- Name: COLUMN travel_requests.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.status IS 'ステータス';


--
-- Name: COLUMN travel_requests.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.created_at IS '作成日時';


--
-- Name: COLUMN travel_requests.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.travel_requests.updated_at IS '更新日時';


--
-- Name: travel_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.travel_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: travel_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.travel_requests_id_seq OWNED BY public.travel_requests.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(100) NOT NULL,
    department_id bigint NOT NULL,
    position_id bigint,
    role character varying(20) DEFAULT 'USER'::character varying NOT NULL,
    slack_id character varying(50),
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    active boolean DEFAULT true NOT NULL,
    supervisor_id bigint
);



--
-- Name: TABLE users; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.users IS 'ユーザーマスタ';


--
-- Name: COLUMN users.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.id IS 'ユーザーID';


--
-- Name: COLUMN users.username; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.username IS 'ユーザー名';


--
-- Name: COLUMN users.password; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.password IS 'パスワード（ハッシュ化）';


--
-- Name: COLUMN users.email; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.email IS 'メールアドレス';


--
-- Name: COLUMN users.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.name IS '氏名';


--
-- Name: COLUMN users.department_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.department_id IS '所属部署ID';


--
-- Name: COLUMN users.position_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.position_id IS '役職ID';


--
-- Name: COLUMN users.role; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.role IS 'ロール（ADMIN/MANAGER/USER）';


--
-- Name: COLUMN users.slack_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.slack_id IS 'Slack ユーザーID';


--
-- Name: COLUMN users.created_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.created_by IS '作成者ID';


--
-- Name: COLUMN users.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.created_at IS '作成日時';


--
-- Name: COLUMN users.updated_by; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.updated_by IS '更新者ID';


--
-- Name: COLUMN users.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.updated_at IS '更新日時';


--
-- Name: COLUMN users.supervisor_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.users.supervisor_id IS '上長ユーザーID';


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: application_approvals id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approvals ALTER COLUMN id SET DEFAULT nextval('public.application_approvals_id_seq'::regclass);


--
-- Name: application_attachments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_attachments ALTER COLUMN id SET DEFAULT nextval('public.application_attachments_id_seq'::regclass);


--
-- Name: application_types id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_types ALTER COLUMN id SET DEFAULT nextval('public.application_types_id_seq'::regclass);


--
-- Name: applications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications ALTER COLUMN id SET DEFAULT nextval('public.applications_id_seq'::regclass);


--
-- Name: approval_flows id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_flows ALTER COLUMN id SET DEFAULT nextval('public.approval_flows_id_seq'::regclass);


--
-- Name: approval_routes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes ALTER COLUMN id SET DEFAULT nextval('public.approval_routes_id_seq'::regclass);


--
-- Name: approval_steps id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_steps ALTER COLUMN id SET DEFAULT nextval('public.approval_steps_id_seq'::regclass);


--
-- Name: audit_logs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs ALTER COLUMN id SET DEFAULT nextval('public.audit_logs_id_seq'::regclass);


--
-- Name: departments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments ALTER COLUMN id SET DEFAULT nextval('public.departments_id_seq'::regclass);


--
-- Name: positions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positions ALTER COLUMN id SET DEFAULT nextval('public.positions_id_seq'::regclass);


--
-- Name: system_logs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_logs ALTER COLUMN id SET DEFAULT nextval('public.system_logs_id_seq'::regclass);

--
-- Name: application_approval_routes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approval_routes ALTER COLUMN id SET DEFAULT nextval('public.application_approval_routes_id_seq'::regclass);

--
-- Name: travel_request_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_request_details ALTER COLUMN id SET DEFAULT nextval('public.travel_request_details_id_seq'::regclass);

--
-- Name: expense_request_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expense_request_details ALTER COLUMN id SET DEFAULT nextval('public.expense_request_details_id_seq'::regclass);

--
-- Name: department_responsible_users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department_responsible_users ALTER COLUMN id SET DEFAULT nextval('public.department_responsible_users_id_seq'::regclass);


--
-- Name: travel_requests id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_requests ALTER COLUMN id SET DEFAULT nextval('public.travel_requests_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: application_approvals application_approvals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approvals
    ADD CONSTRAINT application_approvals_pkey PRIMARY KEY (id);


--
-- Name: application_attachments application_attachments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_attachments
    ADD CONSTRAINT application_attachments_pkey PRIMARY KEY (id);


--
-- Name: application_types application_types_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_types
    ADD CONSTRAINT application_types_code_key UNIQUE (code);


--
-- Name: application_types application_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_types
    ADD CONSTRAINT application_types_pkey PRIMARY KEY (id);


--
-- Name: applications applications_application_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT applications_application_number_key UNIQUE (application_number);


--
-- Name: applications applications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT applications_pkey PRIMARY KEY (id);


--
-- Name: approval_flows approval_flows_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_flows
    ADD CONSTRAINT approval_flows_pkey PRIMARY KEY (id);


--
-- Name: approval_routes approval_routes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes
    ADD CONSTRAINT approval_routes_pkey PRIMARY KEY (id);


--
-- Name: approval_steps approval_steps_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_steps
    ADD CONSTRAINT approval_steps_pkey PRIMARY KEY (id);


--
-- Name: audit_logs audit_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (id);


--
-- Name: department_responsible_users department_responsible_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department_responsible_users
    ADD CONSTRAINT department_responsible_users_pkey PRIMARY KEY (id);


--
-- Name: departments departments_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT departments_code_key UNIQUE (code);


--
-- Name: departments departments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);


--
-- Name: application_approval_routes pk_application_approval_routes; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approval_routes
    ADD CONSTRAINT pk_application_approval_routes PRIMARY KEY (id);


--
-- Name: positions positions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positions
    ADD CONSTRAINT positions_pkey PRIMARY KEY (id);


--
-- Name: positions positions_position_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positions
    ADD CONSTRAINT positions_position_code_key UNIQUE (position_code);


--
-- Name: system_logs system_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_logs
    ADD CONSTRAINT system_logs_pkey PRIMARY KEY (id);


--
-- Name: travel_request_details travel_request_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_request_details
    ADD CONSTRAINT travel_request_details_pkey PRIMARY KEY (id);

-- Name: expense_request_details expense_request_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE ONLY public.expense_request_details
    ADD CONSTRAINT expense_request_details_pkey PRIMARY KEY (id);


--
-- Name: travel_requests travel_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_requests
    ADD CONSTRAINT travel_requests_pkey PRIMARY KEY (id);


--
-- Name: approval_routes uk_approval_routes; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes
    ADD CONSTRAINT uk_approval_routes UNIQUE (application_type_id, step_order);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: idx_app_approval_routes_approver_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_app_approval_routes_approver_id ON public.application_approval_routes USING btree (approver_id);


--
-- Name: idx_application_approvals_application_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_application_approvals_application_id ON public.application_approvals USING btree (application_id);


--
-- Name: idx_application_approvals_approver_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_application_approvals_approver_id ON public.application_approvals USING btree (approver_id);


--
-- Name: idx_application_approvals_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_application_approvals_status ON public.application_approvals USING btree (status);


--
-- Name: idx_application_attachments_application_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_application_attachments_application_id ON public.application_attachments USING btree (application_id);


--
-- Name: idx_applications_applicant_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_applications_applicant_id ON public.applications USING btree (applicant_id);


--
-- Name: idx_applications_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_applications_status ON public.applications USING btree (status);


--
-- Name: idx_audit_logs_created_at; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_audit_logs_created_at ON public.audit_logs USING btree (created_at);


--
-- Name: idx_audit_logs_operation; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_audit_logs_operation ON public.audit_logs USING btree (operation);


--
-- Name: idx_audit_logs_record_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_audit_logs_record_id ON public.audit_logs USING btree (record_id);


--
-- Name: idx_audit_logs_table_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_audit_logs_table_name ON public.audit_logs USING btree (table_name);


--
-- Name: idx_audit_logs_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_audit_logs_user_id ON public.audit_logs USING btree (user_id);


--
-- Name: idx_department_responsible_department_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_department_responsible_department_id ON public.department_responsible_users USING btree (department_id);


--
-- Name: idx_department_responsible_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_department_responsible_user_id ON public.department_responsible_users USING btree (user_id);


--
-- Name: idx_departments_manager_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_departments_manager_id ON public.departments USING btree (manager_id);


--
-- Name: idx_departments_parent_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_departments_parent_id ON public.departments USING btree (parent_id);


--
-- Name: idx_positions_display_order; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_positions_display_order ON public.positions USING btree (display_order);


--
-- Name: idx_positions_hierarchy_level; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_positions_hierarchy_level ON public.positions USING btree (hierarchy_level);


--
-- Name: idx_system_logs_created_at; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_system_logs_created_at ON public.system_logs USING btree (created_at);


--
-- Name: idx_system_logs_level; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_system_logs_level ON public.system_logs USING btree (log_level);


--
-- Name: idx_system_logs_logger; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_system_logs_logger ON public.system_logs USING btree (category);


--
-- Name: idx_system_logs_request_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_system_logs_request_id ON public.system_logs USING btree (request_id);


--
-- Name: idx_system_logs_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_system_logs_user_id ON public.system_logs USING btree (user_id);


--
-- Name: idx_travel_requests_applicant_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_travel_requests_applicant_id ON public.travel_requests USING btree (applicant_id);


--
-- Name: idx_travel_requests_created_at; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_travel_requests_created_at ON public.travel_requests USING btree (created_at);


--
-- Name: idx_travel_requests_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_travel_requests_status ON public.travel_requests USING btree (status);


--
-- Name: idx_users_department_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_department_id ON public.users USING btree (department_id);


--
-- Name: idx_users_position_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_position_id ON public.users USING btree (position_id);


--
-- Name: idx_users_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_role ON public.users USING btree (role);


--
-- Name: idx_users_slack_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_slack_id ON public.users USING btree (slack_id);


--
-- Name: idx_users_supervisor_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_supervisor_id ON public.users USING btree (supervisor_id);


--
-- Name: application_approval_routes update_application_approval_routes_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_application_approval_routes_updated_at BEFORE UPDATE ON public.application_approval_routes FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: application_approvals update_application_approvals_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_application_approvals_updated_at BEFORE UPDATE ON public.application_approvals FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: application_attachments update_application_attachments_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_application_attachments_updated_at BEFORE UPDATE ON public.application_attachments FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: application_types update_application_types_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_application_types_updated_at BEFORE UPDATE ON public.application_types FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: applications update_applications_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_applications_updated_at BEFORE UPDATE ON public.applications FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: approval_routes update_approval_routes_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_approval_routes_updated_at BEFORE UPDATE ON public.approval_routes FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: departments update_departments_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_departments_updated_at BEFORE UPDATE ON public.departments FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: positions update_positions_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_positions_updated_at BEFORE UPDATE ON public.positions FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: travel_request_details update_travel_request_details_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_travel_request_details_updated_at BEFORE UPDATE ON public.travel_request_details FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();

-- Name: expense_request_details update_expense_request_details_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--
CREATE TRIGGER update_expense_request_details_updated_at BEFORE UPDATE ON public.expense_request_details FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: travel_requests update_travel_requests_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_travel_requests_updated_at BEFORE UPDATE ON public.travel_requests FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: users update_users_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: application_approval_routes fk_application_approval_routes_app; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approval_routes
    ADD CONSTRAINT fk_application_approval_routes_app FOREIGN KEY (application_id) REFERENCES public.applications(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_approval_routes fk_application_approval_routes_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approval_routes
    ADD CONSTRAINT fk_application_approval_routes_user FOREIGN KEY (approver_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_approvals fk_application_approvals_application_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approvals
    ADD CONSTRAINT fk_application_approvals_application_id FOREIGN KEY (application_id) REFERENCES public.applications(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_approvals fk_application_approvals_approver_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approvals
    ADD CONSTRAINT fk_application_approvals_approver_id FOREIGN KEY (approver_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_approvals fk_application_approvals_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approvals
    ADD CONSTRAINT fk_application_approvals_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_approvals fk_application_approvals_delegated_to; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approvals
    ADD CONSTRAINT fk_application_approvals_delegated_to FOREIGN KEY (delegated_to) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_approvals fk_application_approvals_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_approvals
    ADD CONSTRAINT fk_application_approvals_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_attachments fk_application_attachments_application_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_attachments
    ADD CONSTRAINT fk_application_attachments_application_id FOREIGN KEY (application_id) REFERENCES public.applications(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_attachments fk_application_attachments_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_attachments
    ADD CONSTRAINT fk_application_attachments_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_attachments fk_application_attachments_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_attachments
    ADD CONSTRAINT fk_application_attachments_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_types fk_application_types_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_types
    ADD CONSTRAINT fk_application_types_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: application_types fk_application_types_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application_types
    ADD CONSTRAINT fk_application_types_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: applications fk_applications_applicant_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk_applications_applicant_id FOREIGN KEY (applicant_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: applications fk_applications_application_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk_applications_application_type_id FOREIGN KEY (application_type_id) REFERENCES public.application_types(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: applications fk_applications_approver_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk_applications_approver_id FOREIGN KEY (approver_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: applications fk_applications_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk_applications_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: applications fk_applications_department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk_applications_department_id FOREIGN KEY (department_id) REFERENCES public.departments(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: applications fk_applications_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk_applications_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_flows fk_approval_flows_application_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_flows
    ADD CONSTRAINT fk_approval_flows_application_type_id FOREIGN KEY (application_type_id) REFERENCES public.application_types(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_flows fk_approval_flows_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_flows
    ADD CONSTRAINT fk_approval_flows_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_flows fk_approval_flows_department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_flows
    ADD CONSTRAINT fk_approval_flows_department_id FOREIGN KEY (department_id) REFERENCES public.departments(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_flows fk_approval_flows_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_flows
    ADD CONSTRAINT fk_approval_flows_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_routes fk_approval_routes_application_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes
    ADD CONSTRAINT fk_approval_routes_application_type_id FOREIGN KEY (application_type_id) REFERENCES public.application_types(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_routes fk_approval_routes_approver_department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes
    ADD CONSTRAINT fk_approval_routes_approver_department_id FOREIGN KEY (approver_department_id) REFERENCES public.departments(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_routes fk_approval_routes_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes
    ADD CONSTRAINT fk_approval_routes_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_routes fk_approval_routes_specific_approver_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes
    ADD CONSTRAINT fk_approval_routes_specific_approver_id FOREIGN KEY (specific_approver_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_routes fk_approval_routes_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_routes
    ADD CONSTRAINT fk_approval_routes_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_steps fk_approval_steps_approval_flow_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_steps
    ADD CONSTRAINT fk_approval_steps_approval_flow_id FOREIGN KEY (approval_flow_id) REFERENCES public.approval_flows(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_steps fk_approval_steps_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_steps
    ADD CONSTRAINT fk_approval_steps_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: approval_steps fk_approval_steps_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.approval_steps
    ADD CONSTRAINT fk_approval_steps_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: audit_logs fk_audit_logs_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT fk_audit_logs_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: department_responsible_users fk_department_responsible_department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department_responsible_users
    ADD CONSTRAINT fk_department_responsible_department_id FOREIGN KEY (department_id) REFERENCES public.departments(id) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- Name: department_responsible_users fk_department_responsible_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department_responsible_users
    ADD CONSTRAINT fk_department_responsible_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- Name: departments fk_departments_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT fk_departments_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: departments fk_departments_manager_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT fk_departments_manager_id FOREIGN KEY (manager_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: departments fk_departments_parent_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT fk_departments_parent_id FOREIGN KEY (parent_id) REFERENCES public.departments(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: departments fk_departments_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT fk_departments_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: system_logs fk_system_logs_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_logs
    ADD CONSTRAINT fk_system_logs_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: travel_request_details fk_travel_request_details_app; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_request_details
    ADD CONSTRAINT fk_travel_request_details_app FOREIGN KEY (application_id) REFERENCES public.applications(id) DEFERRABLE INITIALLY DEFERRED;

-- Name: expense_request_details fk_expense_request_details_app; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE ONLY public.expense_request_details
    ADD CONSTRAINT fk_expense_request_details_app FOREIGN KEY (application_id) REFERENCES public.applications(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: travel_requests fk_travel_requests_applicant_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_requests
    ADD CONSTRAINT fk_travel_requests_applicant_id FOREIGN KEY (applicant_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: travel_requests fk_travel_requests_approver_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.travel_requests
    ADD CONSTRAINT fk_travel_requests_approver_id FOREIGN KEY (approver_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: users fk_users_created_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: users fk_users_department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_department_id FOREIGN KEY (department_id) REFERENCES public.departments(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: users fk_users_position_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_position_id FOREIGN KEY (position_id) REFERENCES public.positions(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: users fk_users_supervisor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_supervisor_id FOREIGN KEY (supervisor_id) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: users fk_users_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_updated_by FOREIGN KEY (updated_by) REFERENCES public.users(id) DEFERRABLE INITIALLY DEFERRED;


--
-- PostgreSQL database dump complete
--

