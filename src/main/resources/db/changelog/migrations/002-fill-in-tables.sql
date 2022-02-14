INSERT INTO client (first_name, last_name, login, password) VALUES
('John', 'Doe', 'jd@gmail.com', '123456'),
('Mark', 'Full', 'mf@gmail.com', '123456'),
('Alice', 'Cooper', 'ac@gmail.com', '123456'),
('Rose', 'Doe', 'rd@gmail.com', '123456');
commit;

INSERT INTO presentation (name, description, creation_time, start_time, user_id) VALUES
('Spring', 'Spring in action', '2021-11-15', '2022-04-04', 1),
('Java', 'Java in action', '2022-02-11', '', 3),
('Hibernate', 'Hibernate in action', '2022-02-12', '', 3);
commit;
