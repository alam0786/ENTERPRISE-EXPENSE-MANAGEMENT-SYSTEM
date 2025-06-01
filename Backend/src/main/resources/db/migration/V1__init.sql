CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255),
  email VARCHAR(100) UNIQUE NOT NULL,
  is_locked BOOLEAN DEFAULT FALSE,
  provider VARCHAR(50) DEFAULT 'LOCAL',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE user_roles (
  user_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE expenses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  title VARCHAR(255),
  amount DECIMAL(10,2) NOT NULL,
  category VARCHAR(100),
  invoice_url VARCHAR(255),
  status VARCHAR(50) DEFAULT 'DRAFT',
  current_approval_level INT DEFAULT 0,
  submitted_at TIMESTAMP,
  rejected_by VARCHAR(100),
  rejected_at TIMESTAMP,
  rejection_reason TEXT,
  auto_approved BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE approvals (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  expense_id BIGINT,
  approver_id BIGINT,
  status VARCHAR(50),
  level INT,
  approved_at TIMESTAMP,
  FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE,
  FOREIGN KEY (approver_id) REFERENCES users(id) ON DELETE CASCADE
);



CREATE TABLE audit_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  action VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
