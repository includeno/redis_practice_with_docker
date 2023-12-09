create table student
(
    id    int primary key auto_increment,
    name  varchar(20) not null,
    age   int,
    score float
) engine = InnoDB
  default charset = utf8mb4;

insert into student(name, age, score)
values ('Peter', 18, 100);
insert into student(name, age, score)
values ('Tom', 17, 98);
insert into student(name, age, score)
values ('John', 17, 99);
