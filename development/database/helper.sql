# Cursos de cada campus por modalidad y grado
select
    substring_index(group_concat(courses.id), ',', 1) as id,
    courses.keyword,
    courses.name,
    courses.periods,
    courses.modality_id,
    substring_index(group_concat(courses.campus_code_id), ',', 1) as campus_code_id,
    courses.grades_id,
    substring_index(group_concat(courses.hash_code), ',', 1) as hash_code
from courses
    left join campus_codes on campus_codes.id = courses.campus_code_id
group by keyword, name, periods, modality_id, grades_id, campus_codes.campus_id
order by trim(name);


# Usuarios de cada campus por modalidad, grado y rol
select
    uuid() as uuid,
    substring_index(group_concat(users.id), ',', 1) as id,
    users.keyword as userKeyword,
    users.periods as period,
    roles.name as role,
    modality.description as modality,
    campus.name as campus,
    grades.name as grade,
    group_concat(users.keyword_course) as courseKeyword
from users
    left join roles on roles.id = users.roles_id
    left join modality on modality.id = users.modality_id
    left join campus_codes on campus_codes.id = users.campus_code_id
    left join campus on campus.id = campus_codes.campus_id
    left join grades on grades.id = users.grades_id
group by users.keyword, users.periods, roles.id, modality.id, campus.id, grades.id
order by users.keyword, users.periods, roles.id, campus.id, modality.id;


# Cantidad de usuarios por campus, periodo, grado y rol
select
    uuid() as id,
    '' as keyword,
    count(t.keyword) as value
from (
    select
        users.keyword
    from users
        left join campus_codes on campus_codes.id = users.campus_code_id
    where
        campus_codes.campus_id = '1' and
        periods = '20241' and
        grades_id = '2' and
        roles_id = '1'
    group by users.keyword
) as t;

# Cantidad de cursos por campus, periodo y grado
select
    uuid() as id,
    '' as keyword,
    count(t.keyword) as value
from (
    select
        courses.hash_code as keyword
    from courses
        left join campus_codes on campus_codes.id = courses.campus_code_id
    where
        campus_codes.campus_id = '1' and
        periods = '20241' and
        grades_id = '1'
) as t;