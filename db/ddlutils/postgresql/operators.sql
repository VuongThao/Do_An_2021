DROP OPERATOR + (timestamptz, NUMERIC);
CREATE OPERATOR + ( PROCEDURE = adddays,
LEFTARG = TIMESTAMPTZ, RIGHTARG = NUMERIC,
COMMUTATOR = +);

DROP OPERATOR - (timestamptz, NUMERIC);
CREATE OPERATOR - ( PROCEDURE = subtractdays,
LEFTARG = TIMESTAMPTZ, RIGHTARG = NUMERIC,
COMMUTATOR = -);
