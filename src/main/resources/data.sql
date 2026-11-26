INSERT INTO study_programs (id, code, name, university, capacity, active)
VALUES (1, 'CS-BER-01', 'Computer Science', 'Campus University Berlin', 120, TRUE);

INSERT INTO study_programs (id, code, name, university, capacity, active)
VALUES (2, 'MED-HAM-01', 'Medicine', 'Campus University Hamburg', 80, TRUE);

INSERT INTO study_programs (id, code, name, university, capacity, active)
VALUES (3, 'HIS-LEI-01', 'History', 'Campus University Leipzig', 60, FALSE);

ALTER TABLE study_programs ALTER COLUMN id RESTART WITH 4;

