<?php

namespace App\Repositories\Android;

use App\Models\Client;
use App\Models\Marchand;
use App\Models\ SuperMarchand;
use App\Repositories\Android\Interfaces\AndroidRepositoryInterface;

class AndroidRepository implements AndroidRepositoryInterface
{
    protected $client;
    protected $marchand;
    protected $supermarchand;

    public function __construct(Client $client, Marchand $marchand, SuperMarchand $supermarchand )
    {
        $this->client = $client;
        $this->marchand = $marchand;
        $this->supermarchand = $supermarchand;
    }

    /**
     * Get's a entreprise by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->entreprise->findOrfail($id);
    }

    /**
     * Get's a entreprise by her name
     *
     * @param string
     * @return collection
     */

    public function getByName($name)
    {
        return $this->entreprise->where('nom','=',$name)->get();
    }

    /**
     * Get's all entreprises.
     *
     * @return mixed
     * @return collection
     */
    public function all()
    {
        return $this->entreprise->paginate();
    }

    /**
     * Deletes a entreprise.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->entreprise->findOrfail($id)->delete();
        
    }

    /**
     * Creates a entreprise.
     *
     * @param array
     */
    public function create( array $entreprise_data)
    {
        $entreprise =new Entreprise;
          
        $entreprise->nom= $entreprise_data['nom'];
        $entreprise->telephone= $entreprise_data['telephone'];
        $entreprise->adresse= $entreprise_data['adresse'];
        $entreprise->description= $entreprise_data['description'];
        $entreprise->email= $entreprise_data['email'];
            
        $entreprise->save();
    }

    /**
     * Updates a entreprise.
     *
     * @param int
     * @param array
     */
    public function update($id, array $entreprise_data)
    {
        $entreprise = $this->entreprise->findOrfail($id);
            
        $entreprise->nom= $entreprise_data['nom'];
        $entreprise->telephone= $entreprise_data['telephone'];
        $entreprise->adresse= $entreprise_data['adresse'];
        $entreprise->description= $entreprise_data['description'];
        $entreprise->email= $entreprise_data['email'];
            
        $entreprise->update();
    }

}

