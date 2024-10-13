-- Создаем базу данных "Кофейня"
CREATE DATABASE Kofeinia;

-- Таблица для ассортимента кафе (напитки и десерты)
CREATE TABLE Menu (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50), -- 'drink' или 'dessert'
    name_en VARCHAR(100) NOT NULL,
    name_other_language VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Таблица для информации о персонале
CREATE TABLE Staff (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    position VARCHAR(50) CHECK (position IN ('Бариста', 'Официант', 'Кондитер'))
);

-- Таблица для информации о клиентах
CREATE TABLE Clients (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    phone VARCHAR(20),
    email VARCHAR(100),
    discount DECIMAL(5, 2) DEFAULT 0
);

-- Таблица для расписания работы персонала
CREATE TABLE StaffSchedule (
    id SERIAL PRIMARY KEY,
    staff_id INT REFERENCES Staff(id),
    work_date DATE,
    start_time TIME,
    end_time TIME
);

-- Таблица для информации о заказах
CREATE TABLE Orders (
    id SERIAL PRIMARY KEY,
    client_id INT REFERENCES Clients(id),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL
);

-- Таблица для деталей заказа (что заказано)
CREATE TABLE OrderDetails (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES Orders(id),
    menu_item_id INT REFERENCES Menu(id),
    quantity INT NOT NULL
);
