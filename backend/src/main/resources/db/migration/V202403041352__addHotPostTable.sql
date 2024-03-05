CREATE TABLE hot_post (
      id bigint primary key auto_increment,
      post_id bigint unique,
      created_time datetime,
      updated_time datetime,
      FOREIGN KEY (post_id) REFERENCES post(id)
);

ALTER TABLE post ADD COLUMN is_hot_post boolean;