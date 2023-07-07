--4.
SELECT order#, TO_CHAR(SUM(quantity * paideach), '$999.99') AS " Total Due"
FROM orderitems
GROUP BY order#
HAVING SUM(quantity * paideach) >
(SELECT SUM(quantity * paideach)
FROM orderitems
WHERE order# = 1008);

--5.
SELECT AuthorID || ': ' || INITCAP(Fname) || ' ' || INITCAP (Lname) AS "Author"
FROM author
JOIN bookauthor 
USING (AuthorID)
WHERE isbn = 
(SELECT isbn 
FROM orderitems
GROUP BY isbn
HAVING COUNT(*) = 
(SELECT MAX(COUNT(*))
FROM orderitems
GROUP BY isbn));

--9.
SELECT COUNT(DISTINCT c.customer#) AS "Number of Customers"
FROM customers c, orders o, orderitems oi
WHERE o.order# = oi.order#
AND c.customer# = o.customer#
AND isbn IN 
(SELECT isbn 
FROM author 
JOIN bookauthor 
USING (authorid)
WHERE Lname = 'AUSTIN'
AND Fname = 'JAMES');

--1.
SELECT b.Title, TO_CHAR(b.Cost, '$999.99') As "COST", category
FROM BOOKS b
WHERE b.Cost >= ALL(SELECT b1.Cost
FROM BOOKS b1
WHERE b.Category = b1.Category);

--2.
SELECT Category, 
TO_CHAR(SUM((Paideach - Cost) *  Quantity),'$999.99') AS "Profit",
RANK() OVER (ORDER BY SUM((paideach - cost)*quantity) DESC) RANK
FROM ORDERITEMS NATURAL JOIN BOOKS
GROUP BY Category;


--3.
SELECT * 
FROM 
(SELECT Customer# || ': ' || INITCAP(Firstname) || ' ' || INITCAP (Lastname)
 AS "Customer", 
TO_CHAR(SUM(Paideach*Quantity) ,'$999.99') AS "Cost",
RANK() OVER (ORDER BY SUM(paideach * quantity) DESC) RANK
FROM CUSTOMERS NATURAL JOIN ORDERS NATURAL JOIN ORDERITEMS
GROUP BY Customer#, Firstname, Lastname)
WHERE rank <= 10;


--4.
SELECT DISTINCT Customer# || ': ' || Firstname || ' ' || Lastname AS "Customer"
FROM CUSTOMERS NATURAL JOIN ORDERS NATURAL JOIN ORDERITEMS NATURAL JOIN BOOKS
WHERE (Category = 'FAMILY LIFE' or Category = 'CHILDREN') AND Customer# NOT IN (
SELECT Customer# 
FROM CUSTOMERS NATURAL JOIN ORDERS NATURAL JOIN ORDERITEMS NATURAL JOIN BOOKS
WHERE Category = 'FITNESS' or Category = 'COOKING');

--5.
--a
ALTER TABLE promotion
ADD (value NUMBER(4));

UPDATE promotion
SET value = (CASE
WHEN gift = 'BOOKMARKER' THEN 1
WHEN gift = 'BOOK LABELS' THEN 2
WHEN gift = 'BOOK COVER' THEN 3
WHEN gift = 'FREE SHIPPING' THEN 15
END)
WHERE gift IN ('BOOKMARKER', 'BOOK LABELS', 'BOOK COVER', 'FREE SHIPPING');

--b
SELECT gift AS "Gift", TO_CHAR(minretail, '$99999.99') AS "Min Retail" , TO_CHAR(maxretail, '$99999.99') AS "Max Retail", value AS "Value"
FROM promotion;


