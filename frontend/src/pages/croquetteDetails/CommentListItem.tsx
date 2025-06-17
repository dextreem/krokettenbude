import styled from "styled-components";
import type { CommentResponse } from "../../api-client";

const user = "Dominic"; // TODO: actual values
const date = new Date().toLocaleString();

const StyledDiv = styled.div`
  display: grid;
  grid-template-columns: 14rem 1fr;
  gap: 1.2rem;
`;

const UserDateDiv = styled.div`
  display: grid;
`;

const StyledSpan = styled.span`
  font-weight: bold;
`;

function CommentListItem({ commentItem }: { commentItem: CommentResponse }) {
  return (
    <StyledDiv>
      <UserDateDiv>
        <StyledSpan>{user}</StyledSpan>
        <span>on {date}</span>
      </UserDateDiv>
      <span>{commentItem.comment}</span>
    </StyledDiv>
  );
}

export default CommentListItem;
