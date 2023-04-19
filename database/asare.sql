CREATE DATABASE asare;
\c asare

CREATE TABLE users (idUser serial PRIMARY KEY , name VARCHAR(255) NOT NULL,
                    lastname VARCHAR(255) NOT NULL, company VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL, idAprox INT, FOREIGN KEY (idAprox) REFERENCES approximations(idAprox) ON DELETE CASCADE );

CREATE TABLE approximations(idAprox serial PRIMARY KEY, name VARCHAR(255) NOT NULL ,totalCost DECIMAL(19,2) NOT NULL,
                            numberMaterials INT NOT NULL,
                            numberServices INT NOT NULL, description VARCHAR(255) NOT NULL, date timestamp NOT NULL, idMaterial INT, idService INT,
                            FOREIGN KEY (idMaterial) REFERENCES materials(idMaterial) ON DELETE SET NULL , FOREIGN KEY (idService) REFERENCES services(idService)
                                ON DELETE SET NULL );

CREATE TABLE materials(idMaterial serial PRIMARY KEY, name VARCHAR(255) NOT NULL, unitCost DECIMAL(19,2) NOT NULL,
                       description VARCHAR(255) NOT NULL, amount INT NOT NULL);

CREATE TABLE services(idService serial PRIMARY KEY, name VARCHAR(255) NOT NULL, unitCost DECIMAL(19,2) NOT NULL,
                      description VARCHAR(255) NOT NULL, amount INT NOT NULL);


CREATE TABLE materials_logs(idMaterialLog INT, name VARCHAR(255) NOT NULL, unitCost DECIMAL(19,2) NOT NULL,
                            description VARCHAR(255) NOT NULL, amount INT NOT NULL);

CREATE TABLE services_logs(idServiceLog INT, name VARCHAR(255) NOT NULL, unitCost DECIMAL(19,2) NOT NULL,
                           description VARCHAR(255) NOT NULL, amount INT NOT NULL);

CREATE USER app_user WITH PASSWORD 'app_user_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON users, approximations, materials,materials_logs, services, services_logs,
    approximations_idaprox_seq, users_iduser_seq, materials_idmaterial_seq, services_idservice_seq TO app_user;

CREATE FUNCTION delete_material_trigger()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO materials_logs VALUES (OLD.idMaterial, old.name, old.unitCost, old.description, old.amount);
RETURN old;
END
$$;


CREATE TRIGGER delete_material_logs AFTER DELETE ON materials FOR EACH ROW EXECUTE PROCEDURE delete_material_trigger();



CREATE FUNCTION delete_service_trigger()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO services_logs VALUES (OLD.idService, old.name, old.unitCost, old.description, old.amount);
RETURN old;
END
$$;



CREATE TRIGGER delete_service_logs AFTER DELETE ON services FOR EACH ROW EXECUTE PROCEDURE delete_service_trigger();