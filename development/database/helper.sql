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
    keyword as userKeyword,
    periods as period,
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
group by users.keyword, users.periods, roles.id, modality.id, campus.id, grades.id;