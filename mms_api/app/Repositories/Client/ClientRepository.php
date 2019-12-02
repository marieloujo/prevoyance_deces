<?php

namespace App\Repositories\Client;

use App\Models\Client;
use App\Repositories\Client\Interfaces\ClientRepositoryInterface;

class ClientRepository implements ClientRepositoryInterface
{
    protected $client;

    public function __construct(Client $client)
    {
        $this->client = $client;
    }
    /**
     * Get a client by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->client->findOrfail($id);
    }

    /**
     * Get a client by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all clients.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->client->paginate();
    }

    /**
     * Delete a client.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->client->findOrfail($id)->delete();
        
    }

    /**
     * Register a client.
     *
     * @param array
     */
    public function create(array $client_data)
    {
            $client = new Client();
            $client->employeur = $client_data['employeur'];
            $client->profession = $client_data['profession'];
            $client->save();

            return $client;
    }

    /**
     * Update a client.
     *
     * @param int
     * @param array
     */
    public function update($id, array $client_data)
    {
            $client =  $this->client->findOrfail($id);
            $client->employeur = $client_data['employeur'];
            $client->profession = $client_data['profession'];
            $client->update();
    }

}

