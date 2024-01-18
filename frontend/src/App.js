import './App.css';
import { Route, Routes } from 'react-router-dom';
import { PATH_NAME } from './constants/routes';
import HomePage from './pages/HomePage/HomePage';
import LoginPage from './pages/LoginPage/LoginPage';
import JoinPage from './pages/JoinPage/JoinPage';
import DailryPage from './pages/DailryPage/DailryPage';
import MyPage from './pages/MyPage/MyPage';
import CommunityPage from './pages/CommunityPage/CommunityPage';
import AdminMembersPage from './pages/AdminMembersPage/AdminMembersPage';
import DefaultLayout from './components/common/Layout/DefaultLayout';
import RootLayout from './components/common/Layout/RootLayout/RootLayout';
import FindIdPage from './pages/LoginPage/FindIdPage';

const App = () => {
  return (
    <Routes>
      <Route path={PATH_NAME.Home} element={<HomePage />} />
      <Route element={<RootLayout />}>
        <Route path={PATH_NAME.Login} element={<LoginPage />} />
        <Route path={PATH_NAME.FindId} element={<FindIdPage />} />
        <Route path={PATH_NAME.Join} element={<JoinPage />} />
      </Route>
      <Route element={<DefaultLayout />}>
        <Route path={PATH_NAME.Dailry} element={<DailryPage />} />
        <Route path={PATH_NAME.MyPage} element={<MyPage />} />
        <Route path={PATH_NAME.Community} element={<CommunityPage />} />
        <Route path={PATH_NAME.AdminMembers} element={<AdminMembersPage />} />
      </Route>
    </Routes>
  );
};

export default App;
