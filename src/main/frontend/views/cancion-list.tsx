import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import {
  Button,
  ComboBox,
  Dialog,
  Grid,
  GridColumn,
  GridItemModel,
  TextField,
  NumberField,
  VerticalLayout,
  GridSortColumn,
  GridSortColumnDirectionChangedEvent,
  HorizontalLayout,
} from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { CancionServices } from 'Frontend/generated/endpoints';

import { useEffect, useState } from 'react';

interface CancionDisplay {
  id: string;
  nombre: string;
  genero: string;
  id_genero: string;
  // FIX: Corregido de 'albun' a 'album'
  album: string;
  // FIX: Corregido de 'id_albun' a 'id_album'
  id_album: string;
  url: string;
  tipo: string;
  duracion: string;
}

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Cancion',
  },
};

type CancionFormProps = {
  onCancionSubmit: () => void;
};

function CancionEntryForm({ onCancionSubmit }: CancionFormProps) {
  const nombre = useSignal('');
  const generoId = useSignal('');
  const albumId = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');
  const dialogOpened = useSignal(false);

  const createCancion = async () => {
    try {
      if (
        !nombre.value.trim() ||
        !generoId.value ||
        !albumId.value ||
        !duracion.value ||
        !url.value.trim() ||
        !tipo.value
      ) {
        Notification.show('Todos los campos son obligatorios', {
          duration: 5000,
          position: 'top-center',
          theme: 'error',
        });
        return;
      }

      const parsedGeneroId = parseInt(generoId.value);
      const parsedAlbumId = parseInt(albumId.value);
      const parsedDuracion = parseInt(duracion.value);

      if (isNaN(parsedGeneroId) || isNaN(parsedAlbumId) || isNaN(parsedDuracion) || parsedDuracion <= 0) {
        Notification.show('Valores de Género, Álbum o Duración inválidos', {
          duration: 5000,
          position: 'top-center',
          theme: 'error',
        });
        return;
      }

      await CancionServices.create(nombre.value, parsedGeneroId, parsedDuracion, url.value, tipo.value, parsedAlbumId);
      onCancionSubmit();

      nombre.value = '';
      generoId.value = '';
      albumId.value = '';
      duracion.value = '';
      url.value = '';
      tipo.value = '';
      dialogOpened.value = false;
      Notification.show('Canción creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  const listaGenero = useSignal<{ value: string; label: string }[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumGenero().then((data) => {
      listaGenero.value = data.map((item) => ({ value: item.value || '', label: item.label || '' }));
    });
  }, []);

  const listaAlbum = useSignal<{ value: string; label: string }[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumCombo().then((data) => {
      listaAlbum.value = data.map((item) => ({ value: item.value || '', label: item.label || '' }));
    });
  }, []);

  const listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listTipo().then((data) => (listaTipo.value = data));
  }, []);

  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva Canción"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}>
              Cancelar
            </Button>
            <Button onClick={createCancion} theme="primary">
              Registrar
            </Button>
          </>
        }>
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre de la canción"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox
            label="Género"
            items={listaGenero.value}
            itemValuePath="value"
            itemLabelPath="label"
            value={generoId.value}
            onValueChanged={(evt) => (generoId.value = evt.detail.value)}
          />
          <ComboBox
            label="Álbum"
            items={listaAlbum.value}
            itemValuePath="value"
            itemLabelPath="label"
            value={albumId.value}
            onValueChanged={(evt) => (albumId.value = evt.detail.value)}
          />
          <ComboBox
            label="Tipo"
            items={listaTipo.value}
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <NumberField
            label="Duración (segundos)"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            min={1}
          />
          <TextField
            label="URL de la canción"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}>
        Agregar
      </Button>
    </>
  );
}

const CancionEntryFormUpdate = function ({
  arguments: cancion,
  onCancionSubmit,
}: CancionFormProps & { arguments: CancionDisplay }) {
  const nombre = useSignal(cancion.nombre || '');
  const generoId = useSignal(cancion.id_genero || '');
  const albumId = useSignal(cancion.id_album || '');
  const duracion = useSignal(cancion.duracion || '');
  const tipo = useSignal(cancion.tipo || '');
  const url = useSignal(cancion.url || '');
  const dialogOpened = useSignal(false);

  const listaGenero = useSignal<{ value: string; label: string }[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumGenero().then((data) => {
      listaGenero.value = data.map((item) => ({ value: item.value || '', label: item.label || '' }));
    });
  }, []);

  const listaAlbum = useSignal<{ value: string; label: string }[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumCombo().then((data) => {
      listaAlbum.value = data.map((item) => ({ value: item.value || '', label: item.label || '' }));
    });
  }, []);

  const listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listTipo().then((data) => (listaTipo.value = data));
  }, []);

  const updateCancion = async () => {
    try {
      if (
        !nombre.value.trim() ||
        !generoId.value ||
        !albumId.value ||
        !duracion.value ||
        !url.value.trim() ||
        !tipo.value
      ) {
        Notification.show('Todos los campos son obligatorios', { duration: 3000, theme: 'error' });
        return;
      }

      const parsedGeneroId = parseInt(generoId.value);
      const parsedAlbumId = parseInt(albumId.value);
      const parsedDuracion = parseInt(duracion.value);

      if (isNaN(parsedGeneroId) || isNaN(parsedAlbumId) || isNaN(parsedDuracion) || parsedDuracion <= 0) {
        Notification.show('Valores de Género, Álbum o Duración inválidos', {
          duration: 5000,
          position: 'top-center',
          theme: 'error',
        });
        return;
      }

      await CancionServices.update(
        parseInt(cancion.id) - 1,
        nombre.value,
        parsedGeneroId,
        parsedDuracion,
        url.value,
        tipo.value,
        parsedAlbumId
      );

      onCancionSubmit();
      dialogOpened.value = false;
      Notification.show('Canción actualizada', { duration: 3000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      console.error('Error al actualizar:', error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        modeless
        headerTitle="Actualizar canción"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => (dialogOpened.value = detail.value)}
        footer={
          <>
            <Button onClick={() => (dialogOpened.value = false)}>Cancelar</Button>
            <Button onClick={updateCancion} theme="primary">
              Guardar cambios
            </Button>
          </>
        }>
        <VerticalLayout style={{ width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre" value={nombre.value} onValueChanged={(e) => (nombre.value = e.detail.value)} />
          <ComboBox
            label="Género"
            items={listaGenero.value}
            itemValuePath="value"
            itemLabelPath="label"
            value={generoId.value}
            onValueChanged={(e) => (generoId.value = e.detail.value)}
          />
          <ComboBox
            label="Álbum"
            items={listaAlbum.value}
            itemValuePath="value"
            itemLabelPath="label"
            value={albumId.value}
            onValueChanged={(e) => (albumId.value = e.detail.value)}
          />
          <ComboBox
            label="Tipo"
            value={tipo.value}
            items={listaTipo.value}
            onValueChanged={(e) => (tipo.value = e.detail.value)}
          />
          <NumberField
            label="Duración (segundos)"
            value={duracion.value}
            onValueChanged={(e) => (duracion.value = e.detail.value)}
            min={1}
          />
          <TextField label="URL" value={url.value} onValueChanged={(e) => (url.value = e.detail.value)} />
        </VerticalLayout>
      </Dialog>

      <Button onClick={() => (dialogOpened.value = true)}>Editar</Button>
    </>
  );
};

export default function CancionView() {
  const [items, setItems] = useState<CancionDisplay[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [searchCriterion, setSearchCriterion] = useState('nombre');

  const searchOptions = [
    { label: 'Nombre', value: 'nombre' },
    { label: 'Álbum', value: 'album' },
  ];

  const callData = () => {
    CancionServices.listCancion()
      .then((data) => {
        const formattedData = data.map((item) => item as CancionDisplay);
        setItems(formattedData);
      })
      .catch((error) => {
        console.error('Error al obtener datos iniciales:', error);
        handleError(error);
      });
  };

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      callData();
      return;
    }

    try {
      const result = await CancionServices.buscarPorCriterio(searchCriterion, searchTerm);
      const formattedData = result.map((item) => item as CancionDisplay);
      setItems(formattedData);

      if (formattedData.length === 0) {
        Notification.show('No se encontraron canciones con ese criterio', {
          duration: 3000,
          position: 'top-center',
          theme: 'contrast',
        });
      }
    } catch (error) {
      console.error('Error en la búsqueda:', error);
      handleError(error);
    }
  };

  useEffect(() => {
    callData();
  }, []);

  const handleSort = (e: GridSortColumnDirectionChangedEvent, columnId: string) => {
    const direction = e.detail.value;
    if (direction) {
      const type = direction === 'asc' ? 0 : 1;

      CancionServices.order(columnId, type)
        .then((data) => {
          const formattedData = data.map((item) => item as CancionDisplay);
          setItems(formattedData);
        })
        .catch((error) => {
          console.error('Error al ordenar datos:', error);
          handleError(error);
        });
    } else {
      callData();
    }
  };

  function actionsRenderer({ item }: { item: CancionDisplay }) {
    const handleDelete = async () => {
      try {
        const cancionIndex = parseInt(item.id) - 1;
        if (isNaN(cancionIndex) || cancionIndex < 0) {
          throw new Error('ID de canción inválido para eliminar');
        }
        await CancionServices.delete(cancionIndex);
        callData();
        Notification.show('Canción eliminada', { duration: 3000, position: 'bottom-end', theme: 'success' });
      } catch (error) {
        console.error('Error al eliminar:', error);
        handleError(error);
      }
    };

    return (
      <span style={{ display: 'flex', gap: '8px' }}>
        <CancionEntryFormUpdate arguments={item} onCancionSubmit={callData} />
        <Button theme="error" onClick={handleDelete}>
          Eliminar
        </Button>
      </span>
    );
  }

  function indexRenderer({ model }: { model: GridItemModel<CancionDisplay> }) {
    return <span>{model.index + 1}</span>;
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Lista de Canciones">
        <Group>
          <HorizontalLayout theme="spacing-s" style={{ alignItems: 'baseline' }}>
            <ComboBox
              items={searchOptions}
              itemLabelPath="label"
              itemValuePath="value"
              value={searchCriterion}
              onValueChanged={(e) => setSearchCriterion(e.detail.value)}
            />
            <TextField
              placeholder="Ingrese el valor a buscar..."
              value={searchTerm}
              onValueChanged={(e) => setSearchTerm(e.detail.value)}
              onKeyPress={(e) => {
                if (e.key === 'Enter') {
                  handleSearch();
                }
              }}
            />
            <Button onClick={handleSearch}>Buscar</Button>
          </HorizontalLayout>
          <CancionEntryForm onCancionSubmit={callData} />
        </Group>
      </ViewToolbar>
      <Grid items={items} allRowsVisible>
        <GridColumn renderer={indexRenderer} header="Nro" autoWidth flexGrow={0} />
        <GridSortColumn path="nombre" header="Nombre" onDirectionChanged={(e) => handleSort(e, 'nombre')} />
        <GridSortColumn path="genero" header="Género" onDirectionChanged={(e) => handleSort(e, 'genero')} />
        {/* FIX: El path ahora es 'album' para coincidir con los datos */}
        <GridSortColumn path="album" header="Álbum" onDirectionChanged={(e) => handleSort(e, 'album')} />
        <GridColumn path="tipo" header="Tipo" />
        <GridColumn path="duracion" header="Duración (s)" autoWidth />
        <GridColumn path="url" header="URL" />
        <GridColumn header="Acciones" renderer={actionsRenderer} autoWidth flexGrow={0} />
      </Grid>
    </main>
  );
}
