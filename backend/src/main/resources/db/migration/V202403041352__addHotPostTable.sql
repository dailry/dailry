CREATE TABLE hot_post (
      id bigint primary key auto_increment,
      post_id bigint,
      created_time datetime,
      updated_time datetime,
      FOREIGN KEY (post_id) REFERENCES post(id)
);