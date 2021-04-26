SET SQLBLANKLINES ON
SET DEFINE OFF

-- IDEMPIERE-3952  EntityType and Data Access Level review for tables
-- Dec 27, 2019, 5:14:30 PM CET
ALTER TABLE AD_InfoProcess MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

ALTER TABLE AD_InfoRelated MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

ALTER TABLE AD_SearchDefinition MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

ALTER TABLE AD_Style MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

ALTER TABLE AD_StyleLine MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

ALTER TABLE AD_ToolBarButton MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

ALTER TABLE AD_ZoomCondition MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

ALTER TABLE PA_DocumentStatus MODIFY EntityType VARCHAR2(40) DEFAULT 'U'
;

SELECT register_migration_script('201912271715_IDEMPIERE-3952.sql') FROM dual
;

