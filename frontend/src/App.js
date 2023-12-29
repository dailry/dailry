import './App.css';
import { Route, Routes } from 'react-router-dom';
import { PATH_NAME } from './constants/routes';
import HomePage from './pages/HomePage/HomePage';
import LoginPage from './pages/LoginPage/LoginPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import DailyPage from './pages/DailyPage/DailyPage';
import MyPage from './pages/MyPage/MyPage';
import CommunityPage from './pages/CommunityPage/CommunityPage';
import AdminMembersPage from './pages/AdminMembersPage/AdminMembersPage';
import DefaultLayout from './components/common/Layout/DefaultLayout';

const App = () => {
  return (
    <Routes>
      <Route element={<DefaultLayout />}>
        <Route path={PATH_NAME.Home} element={<HomePage />} />
        <Route path={PATH_NAME.Login} element={<LoginPage />} />
        <Route path={PATH_NAME.Register} element={<RegisterPage />} />
        <Route path={PATH_NAME.Daily} element={<DailyPage />} />
        <Route path={PATH_NAME.MyPage} element={<MyPage />} />
        <Route path={PATH_NAME.Community} element={<CommunityPage />} />
        <Route path={PATH_NAME.AdminMembers} element={<AdminMembersPage />} />
      </Route>
    </Routes>
  );
};

export default App;
