import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Grid, GridColumn, TextField } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { PersonaService, BandaService } from 'Frontend/generated/endpoints';
import Persona from 'Frontend/generated/com/unl/music/base/models/Persona';

export const config: ViewConfig = {
  title: 'Persona',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Persona',
  },
};

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});



export default function PersonaisView() {
  const dataProvider = useDataProvider<Persona>({
    list: () => PersonaService.lisAllPersona(),
  });

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Personas">
        <Group>
         
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn path="usuario" header="Usuario" />
        <GridColumn path="edad" header="Edad">
          {/* {({ item }) => (item.dueDate ? dateFormatter.format(new Date(item.dueDate)) : 'Never')} */}
        </GridColumn>
        {/* <GridColumn path="creationDate" header="Creation Date">
          {({ item }) => dateTimeFormatter.format(new Date(item.creationDate))}
        </GridColumn> */}
      </Grid>
    </main>
  );
}