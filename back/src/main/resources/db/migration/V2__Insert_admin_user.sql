-- Insert admin user for development purposes
-- Password is plain text 'admin' - will need to be hashed by application
INSERT INTO base_user (username, password, dtype)
VALUES ('admin', 'admin', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

INSERT INTO admin (id)
VALUES (1)
ON CONFLICT (id) DO NOTHING;

-- Assign ROLE_ADMIN to the admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM base_user u, user_role r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;