create table staff_info(
staff_no  varchar(64)  not null comment '员工号' not null,
cust_name  varchar(64) comment '姓名',
mobile  varchar(20) comment '电话号码',
cert_code  varchar(64) comment '身份证号'   ,
branch_no  varchar(64) comment '机构号' not null,
branch_name  varchar(128) comment '机构名称',
job_title  varchar(128) comment '岗位名称' ,
PRIMARY KEY (`staff_no`),
index staffNo(branch_no,staff_no)
) comment ='营销人员信息表';