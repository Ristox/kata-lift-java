package kata.lift;

import static kata.lift.Direction.DOWN;
import static kata.lift.Direction.UP;
import static kata.lift.LiftEngineCommand.GO_DOWN;
import static kata.lift.LiftEngineCommand.GO_UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import org.junit.Test;

public class LiftTest {

   private ILiftController lift = new LiftController(0);

   @Test
   public void isAtInitialFloorOfZero_ifLiftStartsAtZero() {
      assertEquals(0, this.lift.getCurrentFloor());
   }

   @Test
   public void isAtInitialFloorOfOne_ifLiftStartsAtOne() {
      LiftController lift = new LiftController(1);
      assertEquals(1, lift.getCurrentFloor());
   }

   @Test
   public void hasNoDirection_initially() {
      assertThat(this.lift.getCurrentDirection()).isEmpty();
   }

   @Test
   public void hasDirectionUp_ifCalledFromAbove() {
      this.lift.call(new Call(2));

      assertThat(lift.getCurrentDirection()).hasValue(UP);
   }

   @Test
   public void hasDirectionDown_ifCalledFromBelow() {
      this.lift.call(new Call(-1));

      assertThat(this.lift.getCurrentDirection()).hasValue(DOWN);
   }

   @Test
   public void hasNoDirection_ifCalledFromTheSameFloor() {
      int floor = 1;
      LiftController lift = new LiftController(floor);

      lift.call(new Call(floor));

      assertThat(lift.getCurrentDirection()).isEmpty();
   }

   @Test
   public void callReturnsCommandToGoUp_ifCalledFromAbove() {
      Optional<LiftEngineCommand> command = lift.call(new Call(1));

      assertThat(command).hasValue(GO_UP);
   }

   @Test
   public void callReturnsCommandToGoDown_ifCalledFromBelow() {
      Optional<LiftEngineCommand> command = lift.call(new Call(-1));

      assertThat(command).hasValue(GO_DOWN);
   }

   @Test
   public void isAtFirstFloor_afterOnFloorOnce_ifGoingUp() {
      lift.call(new Call(1));

      lift.onFloor();

      assertThat(lift.getCurrentFloor()).isEqualTo(1);
   }

   @Test
   public void isAtFloorMinusOne_ifCalledFromFloorMinusOne() {
      lift.call(new Call(-1));

      lift.onFloor();

      assertThat(lift.getCurrentFloor()).isEqualTo(-1);
   }

}
