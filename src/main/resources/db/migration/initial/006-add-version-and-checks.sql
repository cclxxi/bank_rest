-- 1. Optimistic locking
ALTER TABLE accounts
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

ALTER TABLE transactions
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

-- 2. CHECK constraints
ALTER TABLE accounts
    ADD CONSTRAINT chk_accounts_balance_positive
        CHECK (balance >= 0);

ALTER TABLE transactions
    ADD CONSTRAINT chk_transactions_amount_positive
        CHECK (amount > 0);
