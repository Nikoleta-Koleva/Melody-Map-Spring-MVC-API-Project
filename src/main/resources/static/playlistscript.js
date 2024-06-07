const APIController = (function() {
    
    const clientId = '81a14b3ba793459fa4700d1432754cf5';
    const clientSecret = '008552dbb8dd41f1b453f5baf38507a3';


    const _getToken = async () => {

        const result = await fetch('https://accounts.spotify.com/api/token', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/x-www-form-urlencoded', 
                'Authorization' : 'Basic ' + btoa( clientId + ':' + clientSecret)
            },
            body: 'grant_type=client_credentials'
        });

        const data = await result.json();
        return data.access_token;
    }
    
    const _searchTracks = async (token, query) => {
        const result = await fetch(`https://api.spotify.com/v1/search?q=${query}&type=track&limit=10`, {
            method: 'GET',
            headers: { 'Authorization' : 'Bearer ' + token}
        });

        const data = await result.json();
        return data.tracks.items;
    }

    const _getArtist = async (token, artistId) => {
        const result = await fetch(`https://api.spotify.com/v1/artists/${artistId}`, {
            method: 'GET',
            headers: { 'Authorization' : 'Bearer ' + token}
        });

        const data = await result.json();
        return data;
    }


    return {
        getToken() {
            return _getToken();
        },
        searchTracks(token, query) {
            return _searchTracks(token, query);
        },
        getArtist(token, artistId) {
            return _getArtist(token, artistId);
        }
    }
})();

document.addEventListener('DOMContentLoaded', async function() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        window.location.href = '../ui/login.html';
        return;
    }
       await fetchSongs();

});

    async function fetchSongs() {
       const playlistId = getUrlParameter('playlistId');

    const urlReq = "http://";
    try {
            const response = await fetch(`${urlReq}localhost:8080/playlist/${playlistId}/songs`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                        const songs = await response.json();
                         console.log('Songs:', songs);
                        const playlistList = document.getElementById('playlist');
                        playlistList.innerHTML = '';

                         if (Array.isArray(songs)) {
                                                   songs.forEach(song => {
                                                       const listItem = document.createElement('li');
                                                       const removeButton = document.createElement('button');
                                                       removeButton.textContent = 'Remove';
                                                       removeButton.addEventListener('click', () => removeSong(song.id));

                                                     listItem.innerHTML = `<span>${song.title} - ${song.artist.name} - ${song.duration}</span>`;
                                                      listItem.appendChild(removeButton);

                                                       playlistList.appendChild(listItem);
                                                   });
                                               } else {
                                                   // Handle the single playlist object case
                                                   const songs = song;
                                                   const listItem = document.createElement('li');
                                                       const removeButton = document.createElement('button');
                                                        removeButton.textContent = 'Remove';
                                                        removeButton.addEventListener('click', () => removeSong(song.id));

                                                         listItem.innerHTML = `<span>${song.title} - ${song.artist.name} - ${song.duration}</span>`;
                                                          listItem.appendChild(removeButton);

                                                        playlistList.appendChild(listItem);
                                               }
                                } else {
                                    console.error('Error fetching songs:', response.statusText);
                                    alert('Error fetching songs');
                                }
                            } catch (error) {
                                console.error('Network error:', error);
                                alert('Network error');
                            }
                        };


const UIController = (function() {

    const DOMElements = {
        inputSongTitle: '#song-title',
        spotifyResults: '#spotify-results',
        playlist: '#playlist'
    }

    return {

        getInputField() {
            return {
                songTitle: document.querySelector(DOMElements.inputSongTitle),
                spotifyResults: document.querySelector(DOMElements.spotifyResults),
                playlist: document.querySelector(DOMElements.playlist)
            }
        },

        createTrackResult(titleId, title, duration, artistId, artist) {
                    const html = `<li>
                                    <span>${title} - ${artist} - ${duration}</span>
                                    <button onclick="addSong('${titleId}', '${title}', '${duration}', '${artistId}', '${artist}')">Add to Playlist</button>
                                  </li>`;
                    document.querySelector(DOMElements.spotifyResults).insertAdjacentHTML('beforeend', html);
                },


        resetTrackResults() {
            document.querySelector(DOMElements.spotifyResults).innerHTML = '';
        },



        resetPlaylist() {
            document.querySelector(DOMElements.playlist).innerHTML = '';
        }
    }

})();

const APPController = (function(UICtrl, APICtrl) {

    const DOMInputs = UICtrl.getInputField();

    const loadToken = async () => {
        const token = await APICtrl.getToken();
        return token;
    }

   function formatDuration(ms) {
       const minutes = Math.floor(ms / 60000);
       const seconds = ((ms % 60000) / 1000).toFixed(0);
       return `${minutes < 10 ? '0' : ''}${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
   }



    DOMInputs.songTitle.addEventListener('input', async () => {
        const query = DOMInputs.songTitle.value;
        if (query.length > 0) {
            UICtrl.resetTrackResults();
            const token = await loadToken();
            const tracks = await APICtrl.searchTracks(token, query);
            for (const track of tracks) {
                const artistId = track.artists[0].id;
                const artist = await APICtrl.getArtist(token, artistId);
                const duration = formatDuration(track.duration_ms);
                UICtrl.createTrackResult(track.id, track.name, duration, artistId, track.artists[0].name);
            }
        } else {
            UICtrl.resetTrackResults();
        }
    });

    return {
        init() {
            console.log('App is starting');
        }
    }

})(UIController, APIController);

APPController.init();

// Playlist management
let playlist = [];



async function addSong(titleId, title, duration, artistId, artistName) {

 const song = {
         id: titleId,
         title: title,
         duration: duration,
         artist: {
             id: artistId,
             name: artistName,
         }
        };

 console.log("TitleId: " + song.id)
    console.log("Title: " + song.title)
    console.log("Duration: " + song.duration)
    console.log("ArtistId: " + song.artist.id)
    console.log("ArtistName: " + song.artist.name)

    try {

        const response = await fetch('http://localhost:8080/song/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(song)
        });

        if (response.ok) {
            const result = await response.json();
            console.log('Song added successfully:', result);
            // Optionally redirect or clear the form

        } else {
            console.error('Error adding song:', response.statusText);
           // alert('Error adding song');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Network error');
    }
     playlist.push({ titleId, title, artistName, duration });
                    UIController.resetTrackResults();


                     const userId = getUrlParameter('userId');
                     const playlistId = getUrlParameter('playlistId');


                     const songPlaylist = {
                             playlistId: playlistId,
                             songId: titleId
                            };

                            console.log("playlistId: " + songPlaylist.playlistId)
                            console.log("songId: " + songPlaylist.songId)

                     try {
                         const urlReq = "http://";
                         const response = await fetch(`${urlReq}localhost:8080/playlist/${songPlaylist.playlistId}/addSong/${songPlaylist.songId}`, {
                             method: 'POST',
                             headers: {
                                 'Content-Type': 'application/json'
                             },
                             body: JSON.stringify(songPlaylist)
                         });

                         if (response.ok) {
                             try {
                                 const result = await response.json();
                                 console.log('Song added successfully:', result);
                                 alert('Song added successfully');
                                 await fetchSongs();
                             } catch (jsonError) {
                                 console.warn('Response was not JSON, but the request succeeded');
                                 alert('Song added successfully');
                                  await fetchSongs();
                             }
                             // Optionally redirect or clear the form
                         } else {
                             console.error('Song:', response.statusText);
                             alert('Song');
                         }
                     } catch (error) {
                         console.error('Network error:', error);
                         alert('Network error');
                     }
}

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

async function removeSong(songId) {


    console.log("songId: " + songId);
     const playlistId = getUrlParameter('playlistId');

    const urlReq = "http://";

    try {
        const response = await fetch(`${urlReq}localhost:8080/playlist/${playlistId}/removeSong/${songId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            console.log('Song removed successfully');
            alert('Song removed successfully');
            await fetchSongs();

        } else {
            console.error('Error removing song:', response.statusText);
            alert('Error removing song');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Network error');
    }
}


 // Function to get URL parameters
        function getUrlParameter(name) {
            const urlParams = new URLSearchParams(window.location.search);
            return urlParams.get(name);
        }

        // Get userId and playlistName from URL parameters
        const userId = getUrlParameter('userId');
        const playlistId = getUrlParameter('playlistId');

        // Display userId and playlistName
        //document.getElementById('userId').innerText = `User ID: ${userId}`;
        //document.getElementById('playlistId').innerText = `${decodeURIComponent(playlistId)}`;

        // You can now use userId and playlistName in your logic
        console.log('User ID:', userId);
        console.log('PlaylistId:', decodeURIComponent(playlistId));


// Initial display

