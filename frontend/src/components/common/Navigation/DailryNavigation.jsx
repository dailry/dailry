import { useState, useEffect } from 'react';
import * as S from './Navigation.styled';
import NameArea from './NameArea';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import CreateDailry from '../NavigationItem/CreateDailry';
import { NavigationItemIcon } from '../../../assets/svg';
import { getDailry } from '../../../apis/dailryApi';
import { useDailryContext } from '../../../hooks/useDailryContext';

const DailryNavigation = () => {
  const [dailryItem, setDailryItem] = useState([]);
  const [isCreating, setIsCreating] = useState(false);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  useEffect(() => {
    getDailry().then((response) => setDailryItem(response.data));
  }, [isCreating]);

  const handleItemClick = (dailryId) => {
    setCurrentDailry({ dailryId, pageId: 1 });
  };

  const handleAddClick = () => {
    setIsCreating(true);
  };

  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>My Dailry</Text>
      {dailryItem.map(({ id, title }) => {
        return (
          <NavigationItem
            key={id}
            onClick={() => handleItemClick(id)}
            current={currentDailry.dailryId === id}
            icon={<NavigationItemIcon />}
          >
            {title}
          </NavigationItem>
        );
      })}
      {isCreating ? (
        <CreateDailry setIsCreating={setIsCreating} />
      ) : (
        <S.AddDailry onClick={handleAddClick}>+ 새 다일리 만들기</S.AddDailry>
      )}
    </S.NavigationWrapper>
  );
};

export default DailryNavigation;
