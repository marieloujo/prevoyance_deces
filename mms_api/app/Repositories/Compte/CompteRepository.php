<?php

namespace App\Repositories\Compte;

use App\Models\Compte;
use App\Models\Marchand;
use App\Repositories\Compte\Interfaces\CompteRepositoryInterface;
use Carbon\Carbon;
use Illuminate\Support\Facades\Auth;

class CompteRepository implements CompteRepositoryInterface
{
    protected $compte;

    public function __construct(Compte $compte)
    {
        $this->compte = $compte;
    }
    /**
     * Get a assure by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->compte->findOrfail($id);
    }


    public function getCompte()
    {
        return Auth::user()->usereable->comptes->where('compte','credit_virtuel')->sortByDesc('created_at')->first();
    }

    public function getComptes($end){
        //$time=$end;
        //$start = $end->subMonths(3);
        //return $start.' '.$time;
        //return Carbon::now()->addMonths(3);
        //return Compte::where('created_at', '2019-10-08 06:04:49')->get();
        
        return Auth::user()->usereable->comptes->where('created_at','<=',$end)->where('created_at','>=',$end->subMonths(3)); //->whereBetween('created_at', [$end, $end->subMonths(3)]);
    }
    /**
     * Get a assure by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all assurés.
     *
     * @return mixed
     */
    public function all()
    {
        //return Auth::user()->usereable->comptes->whereBetween('created_at', [$start, $end]);
    }

    /**
     * Delete a assure.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->compte->findOrfail($id)->delete();
        
    }

    /**
     * Register a compte.
     *
     * @param array
     */
    public function create(array $compte_data)
    {
            $compte = new Compte();
            $compte->montant = $compte_data['montant'];
            $compte->compte = $compte_data['compte'];
            $compte->compteable_id = $compte_data['compteable_id'];
            $compte->compteable_type = $compte_data['compteable_type'];
            $compte->save();

            return $compte;
    }

    /**
     * Update a compte.
     *
     * @param int
     * @param array
     */
    public function update($id, array $compte_data)
    {
            $compte =  $this->compte->findOrfail($id);
            $compte->montant = $compte_data['montant'];
            $compte->compte = $compte_data['compte'];
            $compte->compteable_id = $compte_data['compteable_id'];
            $compte->compteable_type = $compte_data['compteable_type'];
            $compte->update();
    }

}

