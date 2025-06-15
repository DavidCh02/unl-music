import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, TextField, VerticalLayout, GridSortColumnDirectionChangedEvent } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useSignal } from '@vaadin/hilla-react-signals';
import { useEffect, useState } from 'react';

import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { ArtistaService } from 'Frontend/generated/endpoints';
import Artista from 'Frontend/generated/com/unl/music/base/models/Artista';

export const config: ViewConfig = {
  title: 'Artistas',
  menu: {
    icon: 'vaadin:users',
    order: 1,
    title: 'Artistas',
  },
};

// --- Componente de Formulario Reutilizable para Artistas ---
type ArtistaFormProps = {
  artista?: Artista; // El artista a editar. Si es undefined, es modo creación.
  onSubmit: () => void; // Función para refrescar el grid.
};

function ArtistaForm({ artista, onSubmit }: ArtistaFormProps) {
  const dialogOpened = useSignal(false);
  const nombre = useSignal(artista?.nombre || '');
  const nacionalidad = useSignal(artista?.nacionalidad || '');
  const paises = useSignal<string[]>([]);

  // Se determina el modo (creación o edición) por la existencia del objeto 'artista'.
  const isEditMode = artista != null;

  // Cargar la lista de países al iniciar el componente.
  useEffect(() => {
    ArtistaService.listCountry().then(data => paises.value = data);
  }, []);

  const openDialog = () => {
    // Restablecer los valores solo si estamos en modo de creación al abrir.
    if (!isEditMode) {
      nombre.value = '';
      nacionalidad.value = '';
    } else {
        // Cargar datos del artista en modo edición
        nombre.value = artista.nombre!;
        nacionalidad.value = artista.nacionalidad!;
    }
    dialogOpened.value = true;
  };

  const handleSubmit = async () => {
    try {
      if (nombre.value.trim().length === 0 || nacionalidad.value.trim().length === 0) {
        Notification.show('No se pudo guardar, faltan datos.', { position: 'top-center', theme: 'error' });
        return;
      }

      if (isEditMode) {
        // Llama al servicio de actualización
        await ArtistaService.aupdateArtista(artista.id!, nombre.value, nacionalidad.value);
        Notification.show('Artista actualizado correctamente.', { theme: 'success' });
      } else {
        // Llama al servicio de creación
        await ArtistaService.createArtista(nombre.value, nacionalidad.value);
        Notification.show('Artista creado correctamente.', { theme: 'success' });
      }

      onSubmit(); // Refresca el grid
      dialogOpened.value = false; // Cierra el diálogo

    } catch (error) {
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        headerTitle={isEditMode ? "Editar Artista" : "Nuevo Artista"}
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button onClick={() => (dialogOpened.value = false)} theme="tertiary">
              Cancelar
            </Button>
            <Button onClick={handleSubmit} theme="primary">
              {isEditMode ? "Guardar Cambios" : "Registrar"}
            </Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre del artista"
            placeholder="Ingrese el nombre"
            value={nombre.value}
            onValueChanged={(e) => (nombre.value = e.detail.value)}
          />
          <ComboBox
            label="Nacionalidad"
            placeholder="Seleccione un país"
            items={paises.value}
            value={nacionalidad.value}
            onValueChanged={(e) => (nacionalidad.value = e.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={openDialog} theme={isEditMode ? "tertiary small" : "primary"}>
        {isEditMode ? "Editar" : "Agregar Artista"}
      </Button>
    </>
  );
}

// --- Vista Principal de Artistas ---
export default function ArtistaView() {
  const [items, setItems] = useState<Artista[]>([]);

  const callData = () => {
    ArtistaService.listAll().then(data => setItems(data));
  };

  useEffect(() => {
    callData();
  }, []);

  const order = (e: GridSortColumnDirectionChangedEvent, columnId: string) => {
    const direction = e.detail.value;
    if (direction) {
      // Usamos 0 para ascendente y 1 para descendente para mantener consistencia.
      const type = direction === 'asc' ? 0 : 1;
      ArtistaService.order(columnId, type).then(data => setItems(data));
    } else {
      // Si no hay dirección de ordenamiento, se carga la lista original.
      callData();
    }
  };

  function indexRenderer({ model }: GridItemModel<Artista>) {
    return <span>{model.index + 1}</span>;
  }

  function actionsRenderer({ item }: GridItemModel<Artista>) {
    return <ArtistaForm artista={item} onSubmit={callData} />;
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Lista de Artistas">
        <Group>
          <ArtistaForm onSubmit={callData} />
        </Group>
      </ViewToolbar>
      <Grid items={items} allRowsVisible>
        <GridColumn renderer={indexRenderer} header="Nro" autoWidth flexGrow={0} />
        <GridSortColumn
          path="nombre"
          header="Nombre del Artista"
          onDirectionChanged={(e) => order(e, 'nombres')}
        />
        <GridSortColumn
          path="nacionalidad"
          header="Nacionalidad"
          onDirectionChanged={(e) => order(e, 'nacionalidad')}
        />
        <GridColumn header="Acciones" renderer={actionsRenderer} autoWidth flexGrow={0} />
      </Grid>
    </main>
  );
}
