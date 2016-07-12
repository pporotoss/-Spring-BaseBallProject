select member_id, id, nickname, levelname from member m, level l
where m.level_id = l.level_id
order by member_id;
