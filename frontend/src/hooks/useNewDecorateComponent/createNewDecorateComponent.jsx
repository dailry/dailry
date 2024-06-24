import { commonDecorateComponentProperties } from './properties';

export const getRelativePosition = (e, pageRef) => {
  const parentX = pageRef?.current?.getBoundingClientRect().left.toFixed();
  const parentY = pageRef?.current?.getBoundingClientRect().top.toFixed();

  const { clientX, clientY } = e;

  return { x: clientX - parentX, y: clientY - parentY };
};

export const getCommonDecorateComponentProperties = (position) => {
  return {
    ...commonDecorateComponentProperties,
    id: `decorate-component-${new Date().toISOString()}`,
    position,
    initialStyle: {
      ...commonDecorateComponentProperties.initialStyle,
      position,
    },
  };
};
