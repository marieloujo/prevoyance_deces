<?php

namespace App\Repositories\Portefeuille;

use App\Models\Portefeuille;
use App\Repositories\Portefeuille\Interfaces\PortefeuilleRepositoryInterface;
use Carbon\Carbon;
use Illuminate\Support\Facades\Auth;

class PortefeuilleRepository implements PortefeuilleRepositoryInterface
{
    protected $portefeuille;
    
    public function __construct(Portefeuille $portefeuille)
    {
        $this->portefeuille = $portefeuille;
    }
    /**
     * Get a portefeuille by it's ID
     *
     * @param int
     * @return collection
     */
    public function getById($contrat,$date){

        //return Carbon::now()->addMonths(3);
        return Auth::user()->usereable->contrats->find($contrat)->portefeuilles->where('created_at','<=',$date)->where('created_at','>=',$date->subMonths(3));
    }
    /**
     * Get a portefeuille by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all assurés.
     *
     * @return mixed
     */
    public function all($date)
    {
        return Auth::user()->usereable->contrats->find(163)->portefeuilles->where('created_at','<=',$date)->where('created_at','>=',$date->subMonths(3));
    }

    /**
     * Delete a portefeuille.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->portefeuille->findOrfail($id)->delete();
        
    }

    /**
     * Register a portefeuille.
     *
     * @param array
     */
    public function create(array $portefeuille_data)
    {
            $portefeuille = new portefeuille();
            $portefeuille->montant = $portefeuille_data['montant'];
            $portefeuille->portefeuille = $portefeuille_data['portefeuille'];
            $portefeuille->portefeuilleable_id = $portefeuille_data['portefeuilleable_id'];
            $portefeuille->portefeuilleable_type = $portefeuille_data['portefeuilleable_type'];
            $portefeuille->save();

            return $portefeuille;
    }

    /**
     * Update a portefeuille.
     *
     * @param int
     * @param array
     */
    public function update($id, array $portefeuille_data)
    {
            $portefeuille =  $this->portefeuille->findOrfail($id);
            $portefeuille->montant = $portefeuille_data['montant'];
            $portefeuille->portefeuille = $portefeuille_data['portefeuille'];
            $portefeuille->portefeuilleable_id = $portefeuille_data['portefeuilleable_id'];
            $portefeuille->portefeuilleable_type = $portefeuille_data['portefeuilleable_type'];
            $portefeuille->update();
    }

}

