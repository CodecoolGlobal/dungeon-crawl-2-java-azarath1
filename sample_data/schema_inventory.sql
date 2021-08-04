DROP TABLE IF EXISTS public.inventory;
CREATE TABLE public.inventory (
   id serial NOT NULL PRIMARY KEY,
   player_id integer NOT NULL,
   item_name text NOT NULL,
   object_name text NOT NULL,
   amount integer NOT NULL
);

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);