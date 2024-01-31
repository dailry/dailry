import PropTypes from 'prop-types';
import { useState, useRef, useEffect } from 'react';
import * as S from './NavigationItem.styled';
import Button from '../Button/Button';
import { postDailry } from '../../../apis/dailryApi';
import { NavigationItemIcon } from '../../../assets/svg';
import { useDailryContext } from '../../../hooks/useDailryContext';

const CreateDailry = (props) => {
  const { setIsCreating } = props;
  const inputRef = useRef(null);
  const [title, setTitle] = useState('');
  const { setCurrentDailry } = useDailryContext();

  useEffect(() => {
    inputRef.current.focus();
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    const newTitle = title || '새 다일리';
    postDailry({ title: newTitle }).then((res) => {
      const { id } = res.data;
      setCurrentDailry({ dailryId: id, page: 1 });
      setIsCreating(false);
    });
  };

  const handleChange = (e) => {
    setTitle(e.target.value);
  };

  return (
    <S.CreateForm onSubmit={handleSubmit}>
      <S.IconWrapper>
        <NavigationItemIcon />
      </S.IconWrapper>
      <S.InputArea
        type="text"
        name={'title'}
        onChange={handleChange}
        value={title}
        ref={inputRef}
        placeholder={'다일리 제목'}
      />
      <Button type={'submit'} size={'sm'}>
        확인
      </Button>
    </S.CreateForm>
  );
};

CreateDailry.propTypes = {
  setIsCreating: PropTypes.node,
};

export default CreateDailry;
