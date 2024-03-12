CREATE TABLE hot_hashtag (
      id bigint primary key auto_increment,
      hashtag_id bigint unique,
      post_count bigint,
      created_time datetime,
      updated_time datetime,
      FOREIGN KEY (hashtag_id) REFERENCES hashtag(id)
);