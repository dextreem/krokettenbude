import { useForm } from "react-hook-form";
import {
  CroquetteCreateRequestFormEnum,
  type CroquetteCreateRequest,
} from "../../api-client";
import { useCreateCroquette } from "../../hooks/api/useCroquetteApi";
import Spinner from "../../components/Spinner";
import Input from "../../components/Input";
import styled from "styled-components";
import TextArea from "../../components/TextArea";
import croquetteSeeds from "../../data/croquette_countries_refined.json";

const StyledDiv = styled.div`
  margin: auto;
  padding-top: 2.4rem;
  padding-bottom: 1.2rem;
  max-width: 100rem;

  display: grid;
  gap: 2.4rem;
`;

const StyledForm = styled.form`
  display: grid;
  gap: 1.2rem;
`;

const Row = styled.div`
  display: flex;
  gap: 1.6rem;
  align-items: center;
`;

const RowItem = styled.div`
  flex: 1;
  min-width: 100px;
  max-width: 150px;
  display: flex;
  flex-direction: column;

  label {
    margin-bottom: 0.4rem;
  }
`;

export default function CreateCroquette() {
  const { createCroquette, isCreatingCroquette } = useCreateCroquette();

  const randomSeed =
    croquetteSeeds[Math.floor(Math.random() * croquetteSeeds.length)];

  const { form, ...rest } = randomSeed;

  const defaultValues: CroquetteCreateRequest = {
    ...rest,
    form: form as CroquetteCreateRequestFormEnum,
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CroquetteCreateRequest>({
    defaultValues,
  });
  const onSubmit = async (data: CroquetteCreateRequest) => {
    createCroquette({ croquetteCreateRequest: data });
  };

  if (isCreatingCroquette)
    return (
      <div>
        <Spinner />
        <span>Creating Croquette</span>
      </div>
    );

  return (
    <StyledDiv>
      <StyledForm onSubmit={handleSubmit(onSubmit)}>
        <div>
          <label>Name</label>
          <Input {...register("name", { required: true })} />
          {errors.name && <span>Name is required</span>}
        </div>

        <div>
          <label>Country</label>
          <Input {...register("country", { required: true })} />
          {errors.country && <span>Country is required</span>}
        </div>

        <div>
          <label>Description</label>
          <TextArea {...register("description", { required: true })} />
          {errors.description && <span>Description is required</span>}
        </div>

        <Row>
          <RowItem>
            <label>Crunchiness (1-5)</label>
            <Input
              type="number"
              {...register("crunchiness", { required: true, min: 1, max: 5 })}
            />
            {errors.crunchiness && <span>Crunchiness must be 1-5</span>}
          </RowItem>

          <RowItem>
            <label>Spiciness (1-5)</label>
            <Input
              type="number"
              {...register("spiciness", { required: true, min: 1, max: 5 })}
            />
            {errors.spiciness && <span>Spiciness must be 1-5</span>}
          </RowItem>

          <RowItem>
            <label>Vegan</label>
            <Input type="checkbox" {...register("vegan")} />
          </RowItem>

          <RowItem>
            <label>Form</label>
            <select {...register("form", { required: true })}>
              {Object.values(CroquetteCreateRequestFormEnum).map(
                (formValue) => (
                  <option key={formValue} value={formValue}>
                    {formValue}
                  </option>
                )
              )}
            </select>
            {errors.form && <span>Form is required</span>}
          </RowItem>
        </Row>

        <div>
          <label>Image URL</label>
          <Input {...register("imageUrl", { required: true })} />
          {errors.imageUrl && <span>Image URL is required</span>}
        </div>

        <button type="submit">Create Croquette</button>
      </StyledForm>
    </StyledDiv>
  );
}
