--
-- Create foo table to use in unit tests.
--

CREATE TABLE foo (
  id BIGINT auto_increment NOT NULL,
  name VARCHAR(50) NOT NULL
);

INSERT INTO foo(name) VALUES('foo');
INSERT INTO foo(name) VALUES('bar');
