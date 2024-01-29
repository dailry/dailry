import PropTypes from 'prop-types';
import { useState } from 'react';
import * as S from './NavigationItem.styled';
import Button from '../Button/Button';
import { postDailry } from '../../../apis/dailryApi';
import { NavigationItemIcon } from '../../../assets/svg';

const CreateDailry = (props) => {
  const { setIsCreating } = props;
  const [title, setTitle] = useState('새 다일리');

  const handleSubmit = (e) => {
    e.preventDefault();
    postDailry({ title }).then((res) => {
      setIsCreating(false);
    });
  };

  const handleChange = (e) => {
    setTitle(e.target.value);
  };

  return (
    <S.CreateForm onSubmit={handleSubmit}>
      <NavigationItemIcon />
      <input type="text" name={'title'} onChange={handleChange} value={title} />
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
