import { useState } from 'react';
import FindIdForm from './FindIdForm';
import SentId from './SentId';
import SentPassword from './SentPassword';

const FindIdPage = () => {
  const [userInfo, setUserInfo] = useState({});
  if (userInfo.username) {
    return <SentPassword email={userInfo.email} />;
  }
  return userInfo.email ? (
    <SentId email={userInfo.email} />
  ) : (
    <FindIdForm setUserInfo={setUserInfo} />
  );
};

export default FindIdPage;
