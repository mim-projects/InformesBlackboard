# Cantidad de cursos por campus, periodo y grado
select
    uuid() as id,
    modality_id as modalityId,
    campus_id as campusId,
    null as rolesId,
    count(distinct hash_code) as value
from courses
left join campus_codes on campus_codes.id = courses.campus_code_id
where courses.periods = '20241' and courses.grades_id = '2'
group by campus_id, grades_id, modality_id
order by value desc;



select
    uuid() as id,
    null as modalityId,
    campus_id as campusId,
    roles_id as rolesId,
    count(distinct keyword) as value
from users
left join campus_codes on campus_codes.id = users.campus_code_id
where users.periods = '20241' and users.grades_id = '2'
group by campus_id, grades_id, roles_id
order by value desc;