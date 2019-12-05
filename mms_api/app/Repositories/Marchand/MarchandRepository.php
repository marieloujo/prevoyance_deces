<?php

namespace App\Repositories\Marchand;

use App\Models\Client;
use App\Models\Marchand;
use App\Repositories\Marchand\Interfaces\MarchandRepositoryInterface;
use Illuminate\Database\Eloquent\Builder;

class MarchandRepository implements MarchandRepositoryInterface
{
    protected $marchand;
    protected $marchand_id;

    public function __construct(Marchand $marchand)
    {
        $this->marchand = $marchand;
    }
    /**
     * Get a marchand by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->marchand->findOrfail($id);
    }

    /**
     * Get a marchand by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all marchands.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->marchand->paginate();
    }

    /**
     * Create marchand.
     *
     * @return mixed
     */
    public function create(array $marchand_data)
    {
        $marchand = new Marchand();
        $marchand->matricule = $marchand_data['matricule'];
        $marchand->credit_virtuel = $marchand_data['credit_virtuel'];
        $marchand->commission = $marchand_data['commission'];
        $marchand->super_marchand_id = $marchand_data['super_marchand_id'];
        $marchand->save();

        return $marchand;
    }

    /**
     * Delete a marchand.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->marchand->findOrfail($id)->delete();
        
    }

    /**
     * Update a marchand.
     *
     * @param int
     * @param array
     */
    public function update($id, array $marchand_data)
    {
        $marchand =  $this->marchand->findOrfail($id);
        $marchand->matricule = $marchand_data['matricule'];
        $marchand->credit_virtuel = $marchand_data['credit_virtuel'];
        $marchand->commission = $marchand_data['commission'];
        $marchand->super_marchand_id = $marchand_data['super_marchand_id'];
        $marchand->update();
    }


    public function getClients($id){
        
        // $clients=Client::whereHas('contrats', function(Builder $query){
        //     $query->where('marchand_id',$this->marchand_id);
        // })->orderByDesc('created_at')->paginate();

//        return $clients->find(63)->contrats;//->find(110)->marchand_id;
         return $this->getById($id)->contrats()->distinct('client_id')->paginate(20);
    }

    public function getContrats($marchand,$client){
        
/*         
        $clients=Client::whereHas('contrats', function(Builder $query){
            $query->where('marchand_id',$this->marchand_id);
        })->orderByDesc('created_at')->paginate();
        return $clients->find(63)->contrats;//->find(110)->marchand_id;
 */
        return $this->getById($marchand)->contrats()->where('client_id',$client)-> paginate(20);
    }

}
