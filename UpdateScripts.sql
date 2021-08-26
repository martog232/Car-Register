-- 01.06.2021; Initial DB creation --
-- ############################### --

CREATE TABLE public.sample_entities (
	id int4 NOT NULL,
	name varchar(25) NOT NULL
);
COMMENT ON TABLE public.sample_entities IS 'Sample entities';

-- Column comments

COMMENT ON COLUMN public.sample_entities.id IS 'Identificator';
