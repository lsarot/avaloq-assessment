drop table if exists ROLL;
drop table if exists SIMULATION;


create table IF NOT EXISTS SIMULATION(
    time_stamp numeric,
    rolls numeric,
    sides numeric,
    dice numeric,
    constraint pk_sim primary key (time_stamp)
);

create table IF NOT EXISTS ROLL(
    time_stamp numeric,
    roll_sum numeric,
    qty numeric,
    constraint pk_sums primary key (time_stamp,roll_sum),
    constraint fk_simts foreign key (time_stamp) references simulation (time_stamp)
);


commit;