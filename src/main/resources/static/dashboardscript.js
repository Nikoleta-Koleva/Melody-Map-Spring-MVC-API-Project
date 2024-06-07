document.addEventListener('DOMContentLoaded', async function() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        window.location.href = '../ui/login.html';
        return;
    }

    // Initial fetch to populate the playlist list
    await fetchPlaylists(userId);
});

async function fetchPlaylists(userId) {
    const urlReq = "http://";

    try {
        const response = await fetch(`${urlReq}localhost:8080/playlist/user/${userId}/playlists`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const playlists = await response.json();
            console.log('Playlists:', playlists);
            const playlistList = document.getElementById('playlist-list');
            playlistList.innerHTML = '';

            if (Array.isArray(playlists)) {
                playlists.forEach(playlist => {
                    const listItem = document.createElement('li');
                    listItem.classList.add('playlist-item');

                    const removeButton = document.createElement('button');
                    removeButton.textContent = 'Remove';
                    removeButton.classList.add('remove-button');

                    removeButton.addEventListener('click', (event) => {
                                        event.stopPropagation();
                                        removePlaylist(playlist.id);
                                    });

                    listItem.innerHTML = `<div class="playlist-info">
                                                            <strong>${playlist.name}</strong> -
                                                            ${playlist.description}
                                                          </div>`;

                    listItem.appendChild(removeButton);

                    listItem.addEventListener('click', () => {
                        const url = new URL('../ui/playlist.html', window.location.href);
                        url.searchParams.append('userId', userId);
                        url.searchParams.append('playlistId', encodeURIComponent(playlist.id));
                        console.log('playlistId: ' + playlist.id);
                        window.location.href = url.toString();
                    });

                    playlistList.appendChild(listItem);
                });
            } else {
                // Handle the single playlist object case
                const playlist = playlists;
                const listItem = document.createElement('li');
                listItem.classList.add('playlist-item');

                const removeButton = document.createElement('button');
                removeButton.textContent = 'Remove';
                removeButton.classList.add('remove-button');

                 removeButton.addEventListener('click', (event) => {
                                                       event.stopPropagation();
                                                       removePlaylist(playlist.id);
                                                   });

                listItem.innerHTML = `<strong>${playlist.name}</strong><br>${playlist.description}`;

                listItem.appendChild(removeButton);

                listItem.addEventListener('click', () => {
                    const url = new URL('../ui/playlist.html', window.location.href);
                    url.searchParams.append('userId', userId);
                    url.searchParams.append('playlistId', encodeURIComponent(playlist.id));
                    console.log('playlistId: ' + playlist.id);
                    window.location.href = url.toString();
                });

                playlistList.appendChild(listItem);
            }
        } else {
            console.error('Error fetching playlists:', response.statusText);
            alert('Error fetching playlists');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Network error');
    }
}


let nextPlaylistId = 1; // Global variable to keep track of the next ID
    const userId = localStorage.getItem('userId');


document.getElementById('playlist-form').addEventListener('submit', async (event) => {
    event.preventDefault(); // Prevent the default form submission

    const username = document.getElementById('playlist-name').value;
    const password = document.getElementById('playlist-description').value;

       const name = document.getElementById('playlist-name').value;
        const description = document.getElementById('playlist-description').value;



    const playlist = {
        name: name,
        description: description,
        userId: userId
    };

     console.log('name', playlist.name);
                 console.log('description', playlist.description);
                 console.log('userId', playlist.userId);

    try {
        const response = await fetch('http://localhost:8080/playlist/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(playlist)
        });

        if (response.ok) {
            const result = await response.json();
            console.log('Playlist added successfully:', result);
            alert('Playlist added successfully');
            // Optionally redirect or clear the form
             await fetchPlaylists(userId);
        } else {
            console.error('Error adding playlist:', response.statusText);
            alert('Error adding playlist');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Network error');
    }
});

async function removePlaylist(playlistId) {


    console.log("playlistId to delete: " + playlistId);
     const userId = localStorage.getItem('userId');
    const urlReq = "http://";

    try {
        const response = await fetch(`${urlReq}localhost:8080/playlist/${playlistId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            console.log('Playlist removed successfully');
            alert('Playlist removed successfully');
            await fetchPlaylists(userId);

        } else {
            console.error('Error removing playlist:', response.statusText);
            alert('Error removing playlist');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Network error');
    }
}
