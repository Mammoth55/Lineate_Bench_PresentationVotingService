INSERT INTO public.client (first_name, last_name, login) VALUES
('John', 'Doe', 'jd@gmail.com'),
('Mark', 'Full', 'mf@gmail.com'),
('Alice', 'Cooper', 'ac@gmail.com'),
('Rose', 'Doe', 'rd@gmail.com');
COMMIT;

INSERT INTO public.presentation (name, description, status, creation_time, start_time, client_id) VALUES
('Spring', 'Spring in action', 'PLANNED', '2022-02-04 20:00:00+03', '2022-04-04 20:00:00+03', 1),
('Java', 'Java in action', 'CREATED', '2022-02-11 20:00:00+03', null, 3),
('Hibernate', 'Hibernate in action', 'PUBLISHED', '2022-02-14 01:00:00+03', null, 3);
COMMIT;

INSERT INTO public.vote (vote_status, vote_time, client_id, presentation_id) VALUES
('WANT_LOOK', '2022-02-14 14:00:00+03', 4, 1),
('WANT_LOOK', '2022-02-14 15:00:00+03', 1, 3),
('WANT_LOOK', '2022-02-14 14:00:00+03', 2, 3);
COMMIT;
