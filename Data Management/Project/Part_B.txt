-- #1
COLUMN Employee FORMAT A30
COLUMN Times FORMAT A5
COLUMN Earliest_Date FORMAT A15

SELECT e.emp_num ||': '|| e.fname ||' '|| e.lname AS "Employee", s.name AS "Skill",
    COUNT(s.code) AS "Times", MIN(t.date_acquired) AS "Earliest_Date",
    TRUNC(MONTHS_BETWEEN(SYSDATE, MAX(t.date_acquired))) AS "#Passed_Month"
FROM employee e, training t, skill s
WHERE e.emp_num = t.emp_num
AND t.code = s.code
GROUP BY e.emp_num, e.fname, e.lname, s.name
ORDER BY e.emp_num;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #2
COLUMN Employee FORMAT A40

SELECT LEVEL,
	LPAD(' ', 3*(LEVEL-1)) || emp_num || '    ' || fname || ' ' || lname AS "Employee",
	department.name AS "Dept_Name"
FROM employee JOIN department USING(dept_code)
START WITH emp_num=1001
CONNECT BY PRIOR emp_num = super_id;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #3
COLUMN start_date FORMAT A10
COLUMN Proj_Start FORMAT A10
COLUMN Month FORMAT A6

SELECT a.proj_number || ': ' || p.name AS "Project", p.start_date AS "Proj_Start", TO_CHAR(a.date_assigned,'MM') AS "Month", 
    COUNT(DISTINCT a.emp_num) AS "#Employees", SUM(a.hours_used)
FROM project p, assignment a
WHERE p.proj_number = a.proj_number
AND p.total_cost IS NULL
GROUP BY GROUPING SETS((a.proj_number, p.name, p.start_date), (a.proj_number, p.name, p.start_date, a.date_assigned));


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #4
ALTER TABLE employee
ADD Bonus_Amt NUMBER(4,0);

UPDATE employee
SET Bonus_Amt = CASE WHEN emp_num IN
		(SELECT emp_num
		 FROM project JOIN assignment USING(proj_number)
		 WHERE EXTRACT(YEAR FROM start_date) = EXTRACT(YEAR FROM SYSDATE)
		 AND ROUND(EXTRACT(MONTH FROM start_date)/3) <= 1
		 GROUP BY EMP_NUM
		 HAVING SUM(hours_used) >= 150) THEN 200
		  ELSE 0
		  END;

SELECT * FROM employee;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #5
COLUMN date_acquired FORMAT A15
BREAK ON Employee ON hire_date

SELECT e.emp_num ||': '|| e.fname ||' '|| e.lname AS "Employee", e.hire_date,
    t.name AS "Training_Name", t.date_acquired, t.date_acquired - e.hire_date AS "Days of Training", 
    COUNT(DISTINCT a.proj_number) AS "#Porject"
FROM employee e
LEFT JOIN training t
ON e.emp_num = t.emp_num
LEFT JOIN assignment a
ON e.emp_num = a.emp_num
WHERE e.hire_date >= TO_DATE('2021-04-01','yyyy-mm-dd')
AND e.hire_date <= TO_DATE('2021-06-30','yyyy-mm-dd')
GROUP BY  e.emp_num, e.fname, e.lname, e.hire_date, t.name, t.date_acquired
ORDER BY e.emp_num;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #6
SELECT name, start_date, 
		CASE WHEN total_cost IS NULL THEN 'on-going'
		ELSE 'completed'
		END AS "Status"
FROM (SELECT a1.proj_number, a1.date_assigned, a2.date_assigned, p.name, p.start_date, p.total_cost
	FROM assignment a1
	LEFT JOIN assignment a2 on a1.proj_number = a2.proj_number
	JOIN project p on p.proj_number = a1.proj_number
	WHERE a1.date_assigned != a2.date_assigned
	AND a1.date_assigned > a2.date_assigned
	GROUP BY a1.proj_number, a1.date_assigned, a2.date_assigned, p.name, p.start_date, p.total_cost
	HAVING MONTHS_BETWEEN(a1.date_assigned, a2.date_assigned) > 1
	ORDER BY proj_number, a1.date_assigned, a2.date_assigned);


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #7
COLUMN Quarter FORMAT A10

SELECT TO_CHAR(p.start_date,'Q') quarter, COUNT(DISTINCT p.proj_number) AS "#Project",
    COUNT(DISTINCT a.emp_num)  AS "#Employee", SUM(a.hours_used) / COUNT(DISTINCT p.proj_number) AVG_Hours
FROM project p, assignment a
WHERE p.proj_number = a.proj_number
AND TO_CHAR(p.start_date,'YYYY') = '2021' 
GROUP BY TO_CHAR(p.start_date,'YYYY') ,TO_CHAR(p.start_date,'Q')
ORDER BY TO_CHAR(p.start_date,'YYYY') ,TO_CHAR(p.start_date,'Q');


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #8
SELECT 	DECODE(emp_num, null, '---', emp_num) ID,
		DECODE(emp_name, null, 'Number of Trainings:', emp_name) "Employee Name", 
		NVL(SUM(DECODE(code,01,1)),0) AS Java,
		(CASE
			WHEN emp_name IS NULL THEN '------'
			ELSE MAX(java_da)
			END
		) AS "Latest Date Acquired",
		NVL(SUM(DECODE(code,02,1)),0) AS Advertising,
		(CASE
			WHEN emp_name IS NULL THEN '------'
			ELSE MAX(ad_da)
			END
		) AS "Latest Date Acquired",
		NVL(SUM(DECODE(code,03,1)),0) AS Writing,
		(CASE
			WHEN emp_name IS NULL THEN '------'
			ELSE MAX(writing_da)
			END
		) AS "Latest Date Acquired",
		(CASE
			WHEN emp_name IS NULL THEN '------'
			ELSE TO_CHAR(COUNT(*))
			END
		) AS "Number of Skills"
FROM (SELECT emp_num, fname || ' ' || lname AS emp_name,
		skill.name,
		code,
		training.date_acquired,
	(CASE
		WHEN code = 01 THEN TO_CHAR(MAX(date_acquired), 'DD-MON-YY')
		ELSE '------'
		END
	) AS java_da,
	(CASE
		WHEN code = 02 THEN TO_CHAR(MAX(date_acquired), 'DD-MON-YY')
		ELSE '------'
		END
	) AS ad_da,
	(CASE
		WHEN code = 03 THEN TO_CHAR(MAX(date_acquired), 'DD-MON-YY')
		ELSE '------'
		END
	) AS writing_da
	FROM employee JOIN training USING(emp_num)
	      	      JOIN skill USING(code)
	GROUP BY emp_num, fname || ' ' || lname, skill.name, training.date_acquired, code)
GROUP BY GROUPING SETS( (emp_num, emp_name), ());


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #9
SELECT d.name AS "Department", s.name AS "Skill", NVL(count, 0) AS "#training", 
RANK() OVER (PARTITION BY d.name ORDER BY count DESC NULLS LAST) rank
FROM department d
CROSS JOIN skill s
LEFT JOIN
(SELECT d.name dep_name, s.name skill_name, COUNT(t.train_num) count
FROM skill s
JOIN training t
ON s.code = t.code
JOIN employee e
ON t.emp_num = e.emp_num
JOIN department d
ON e.dept_code = d.dept_code
GROUP BY d.name, s.name)
ON s.name = skill_name
AND d.name = dep_name;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #10
/*
Nested

- Group the records and sum up the now "top 3 records" and eliminate any groups who have less than 60 days in first 3 records/assignments
	- Reduce projects v down to top 3 records
		- Get Projects with > 5 assignment records and sum up total days worked on those projects
*/	
SELECT proj_number, name, Tot_Days_Worked
FROM(SELECT *
     FROM project JOIN (SELECT proj_number, Tot_Days_Worked,
		  (date_ended-date_assigned) AS Assign_Days_Worked, 
		   ROW_NUMBER() OVER (PARTITION BY proj_number ORDER BY date_assigned) rank
		   FROM assignment JOIN (SELECT proj_number, SUM(date_ended - date_assigned) AS Tot_Days_Worked
			FROM assignment
			GROUP BY proj_number
			HAVING (COUNT(*) > 5)
			ORDER BY proj_number)
		USING(proj_number))
	USING(proj_number)
	WHERE rank <= 3)
GROUP BY proj_number, name, tot_days_worked
HAVING SUM(Assign_Days_Worked) >= 60;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #11
COLUMN lname FORMAT A15

SELECT e.lname AS "Employee", e.hire_date, d.name AS "Managed_Dept", 
    s.supervising AS "#supervising"
FROM employee e
LEFT JOIN department d
ON e.dept_code = d.dept_code
LEFT JOIN 
(SELECT super_id, count(*) supervising 
FROM employee
WHERE super_id is NOT NULL
GROUP BY super_id) s
ON e.emp_num = s.super_id
WHERE rownum <= 4
ORDER BY e.hire_date;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #12
/*
1. Map each client to type from web address
2. How many clients in each category
3. how many projects for each category
*/
SELECT type, NVL(client_count,0) "Project Count", NVL(proj_count,0) "Client Count"
FROM (SELECT 'Educational' AS Type FROM dual UNION ALL
	SELECT 'Government' AS Type FROM dual UNION ALL
	SELECT 'Non-Profit' AS Type FROM dual UNION ALL
	SELECT 'For-Profit' AS Type FROM dual UNION ALL
	SELECT 'Other' AS Type FROM dual
	) type_table
LEFT JOIN (SELECT type, COUNT(DISTINCT client_id) client_count, COUNT(DISTINCT proj_number) proj_count
		FROM project FULL OUTER JOIN (SELECT (CASE
					WHEN LOWER(web_address) LIKE '%.edu%' THEN 'Educational'
					WHEN LOWER(web_address) LIKE '%.gov%' THEN 'Government'
					WHEN LOWER(web_address) LIKE '%.org%' THEN 'Non-Profit'
					WHEN LOWER(web_address) LIKE '%.com%' THEN 'For-Profit'
					ELSE 'Other'
					END) Type, client_id
					FROM client) 
	USING(client_id)
	GROUP BY type)
USING(type);


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>

-- #13
COLUMN Last_project FORMAT A15 
SELECT employee, department, proj_number AS "Last_Project"
FROM
(SELECT e.emp_num ||': '|| e.fname ||' '|| e.lname employee, d.name department, 
    p.proj_number, p.start_date,
    ROW_NUMBER() OVER (PARTITION BY e.emp_num ORDER BY p.start_date DESC) rn
FROM employee e
LEFT JOIN assignment a
ON e.emp_num = a.emp_num
LEFT JOIN project p
ON p.proj_number = a.proj_number
JOIN department d 
ON e.dept_code = d.dept_code
WHERE e.emp_num NOT IN 
(SELECT a.emp_num
FROM project p, assignment a
WHERE p.proj_number = a.proj_number
AND p.start_date >= TO_DATE('2021-08-01','yyyy-mm-dd'))
GROUP BY e.emp_num, e.fname, e.lname, d.name, p.proj_number, p.start_date
ORDER BY d.name, e.lname)
WHERE rn = 1;


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #14
--For each skill, list number of employee trainings and number of projects requiring that skill code
SELECT DECODE(skill_name, null, 'Totals:', skill_name) "Skill Name",
       DECODE(sum_proj, null, SUM(sum_proj), sum_proj) "# of Projects requiring skill",  
       DECODE(sum_emp, null, SUM(sum_emp), sum_emp) "# of Employee trainings"
FROM(	SELECT skill_name, sum_proj, COUNT(*) AS sum_emp
	FROM training LEFT JOIN	(select skill.name skill_name, COUNT(*) AS sum_proj, code
		FROM project left join skill using(code)
		GROUP BY skill.name, code)
	USING(code)
	group by skill_name, sum_proj)
GROUP BY GROUPING SETS((skill_name, sum_proj, sum_emp), ());


--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>--<>


-- #15
SET LINESIZE 300
COLUMN table_name FORMAT A15 
COLUMN column_name FORMAT A15 
COLUMN constraint_name FORMAT A30 
COLUMN FK_references FORMAT A30
BREAK ON table_name

SELECT c.table_name, cc.column_name, c.constraint_name, 
    Case 
        WHEN c.constraint_type = 'P' THEN 'PK'
        WHEN c.constraint_type = 'R' THEN 'FK'
        WHEN c.constraint_type = 'C' AND c.constraint_name LIKE '%_CK' THEN 'CK'
        WHEN c.constraint_type = 'C' AND c.constraint_name LIKE '%_NN' THEN 'NN'
        WHEN c.constraint_type = 'U' THEN 'UK'
        ELSE c.constraint_type
    END, 
    c.search_condition, 
    Case c.constraint_type
        WHEN 'R' THEN SUBSTR(c.r_constraint_name, 1, INSTR(c.r_constraint_name, '_') - 1) ||':'|| RTRIM(SUBSTR(c.r_constraint_name, INSTR(c.r_constraint_name, '_') + 1, LENGTH(c.r_constraint_name)), '_PK')
    END AS "FK_references"
FROM user_constraints c 
JOIN user_cons_columns cc 
ON c.table_name = cc.table_name 
AND c.constraint_name = cc.constraint_name 
ORDER BY c.table_name, cc.position;
