// 하는일: 여러 다일리 이동, 삭제, 이름바꾸기, 공유 => 여러 다일리의 id
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';
import * as S from './Navigation.styled';
import NameArea from './NameArea';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import NavigationInput from '../NavigationItem/NavigationInput';
import { NavigationItemIcon } from '../../../assets/svg';
import { getDailry, postDailry, postPage } from '../../../apis/dailryApi';
import DailryHamburger from '../Hamburger/DailryHamburger';
import { PATH_NAME } from '../../../constants/routes';

const DailryNavigation = (props) => {
  const { currentDailryId } = props;
  const [dailrys, setDailrys] = useState([]);
  const [editingDailry, setEditingDailry] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      const response = await getDailry();
      setDailrys(response.data);
    })();
  }, [editingDailry]);

  const handleItemClick = async (targetId) => {
    navigate(`${PATH_NAME.Dailry}/${targetId}/1`);
  };

  const isCurrent = (id) => {
    return currentDailryId === id;
  };

  const handleAddClick = async () => {
    const response = await postDailry({ title: '새 다일리' });
    const { dailryId } = await response.data;
    setEditingDailry(dailryId);
    await postPage(dailryId);
  };

  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>My Dailry</Text>
      {dailrys.map(({ dailryId, title }) => {
        if (editingDailry === dailryId) {
          return (
            <NavigationInput
              key={dailryId}
              dailryId={dailryId}
              title={title}
              setEditingDailry={setEditingDailry}
            />
          );
        }
        return (
          <NavigationItem
            key={dailryId}
            onClick={() => handleItemClick(dailryId)}
            current={isCurrent(dailryId)}
            icon={<NavigationItemIcon />}
            hamburger={
              <DailryHamburger
                setEditingDailry={setEditingDailry}
                dailryId={dailryId}
              />
            }
          >
            {title}
          </NavigationItem>
        );
      })}
      <S.AddDailry onClick={handleAddClick}>+ 새 다일리 만들기</S.AddDailry>
    </S.NavigationWrapper>
  );
};

DailryNavigation.propTypes = {
  currentDailryId: PropTypes.number,
};

export default DailryNavigation;
