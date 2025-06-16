import { Link } from "react-router-dom";
import styled, { css } from "styled-components";
import type { ReactNode } from "react";

type Size = "small" | "medium" | "large";
type Variation = "primary" | "secondary" | "danger";

const sizes = {
  small: css`
    font-size: 1.2rem;
    padding: 0.4rem 0.8rem;
    font-weight: 600;
    text-transform: uppercase;
    text-align: center;
  `,
  medium: css`
    font-size: 1.4rem;
    padding: 1.2rem 1.6rem;
    font-weight: 500;
  `,
  large: css`
    font-size: 1.6rem;
    padding: 1.2rem 2.4rem;
    font-weight: 500;
  `,
};

const variations = {
  primary: css`
    color: var(--color-brand-50);
    background-color: var(--color-brand-600);

    &:hover {
      background-color: var(--color-brand-700);
    }
  `,
  secondary: css`
    color: var(--color-brand-600);
    background-color: var(--color-grey-0);
    border: 1px solid var(--color-grey-200);

    &:hover {
      background-color: var(--color-grey-50);
    }
  `,
  danger: css`
    color: var(--color-red-100);
    background-color: var(--color-red-700);

    &:hover {
      background-color: var(--color-red-800);
    }
  `,
};

interface StyledLinkButtonProps {
  size?: Size;
  variation?: Variation;
}

const StyledLinkButton = styled(Link)<StyledLinkButtonProps>`
  border: none;
  border-radius: var(--border-radius-sm);
  box-shadow: var(--shadow-sm);
  display: inline-block;
  text-decoration: none;

  ${(props) => sizes[props.size || "medium"]};
  ${(props) => variations[props.variation || "primary"]};
`;

interface LinkButtonProps extends StyledLinkButtonProps {
  to: string;
  children: ReactNode;
}

function LinkButton({
  to,
  children,
  variation = "primary",
  size = "medium",
}: LinkButtonProps) {
  return (
    <StyledLinkButton to={to} variation={variation} size={size}>
      {children}
    </StyledLinkButton>
  );
}

export default LinkButton;
